package com.application.portdex.presentation.chat.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.widget.doAfterTextChanged
import com.application.portdex.adapters.chat.ChatAdapter
import com.application.portdex.core.dataBinding.DataBinding.setUserImage
import com.application.portdex.core.enums.MessageType
import com.application.portdex.core.notification.NotificationUtil
import com.application.portdex.core.prefs.ActivityPreference
import com.application.portdex.core.prefs.NotifyPreference
import com.application.portdex.core.utils.GenericUtils.textSpaceWatcher
import com.application.portdex.data.utils.Resource
import com.application.portdex.databinding.ChatActivityBinding
import com.application.portdex.domain.models.ProfileInfo
import com.application.portdex.domain.models.ProviderInfo
import com.application.portdex.domain.models.chat.ChatBody
import com.application.portdex.domain.viewmodels.ChatViewModel
import com.application.portdex.presentation.base.BaseActivity
import com.jacopo.pagury.prefs.PrefUtils
import dagger.hilt.android.AndroidEntryPoint
import org.jivesoftware.smack.packet.Message

@AndroidEntryPoint
class ChatActivity : BaseActivity() {

    companion object {
        const val PROVIDER_ITEM = "ChatActivity.PROVIDER_ITEM"
        const val PROFILE_ITEM = "ChatActivity.PROFILE_ITEM"
    }

    private val viewModel by viewModels<ChatViewModel>()
    private lateinit var mBinding: ChatActivityBinding

    private var userId: String? = null
    private var adapter: ChatAdapter? = null

    //intent
    private val provider by lazy { intent?.getParcelableExtra<ProviderInfo>(PROVIDER_ITEM) }
    private val profile by lazy { intent?.getParcelableExtra<ProfileInfo>(PROFILE_ITEM) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ChatActivityBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        mBinding.actionBack.setOnClickListener { onBackPressed() }

        initIntent()
        initInput()
    }

    private fun initInput() {
        mBinding.inputMessage.textSpaceWatcher()
        mBinding.inputMessage.doAfterTextChanged { text ->
            mBinding.btnSend.isEnabled = !text.isNullOrEmpty()
        }
    }

    private fun initIntent() {
        userId = when {
            !provider?.userId.isNullOrEmpty() -> provider?.userId
            !profile?.userId.isNullOrEmpty() -> profile?.userId
            else -> null
        }
        val userName = when {
            !provider?.firstName.isNullOrEmpty() -> provider?.firstName
            !profile?.firstName.isNullOrEmpty() -> profile?.firstName
            else -> null
        }
        val userImage = when {
            !provider?.profilePicUrl.isNullOrEmpty() -> provider?.profilePicUrl
            !profile?.profilePicUrl.isNullOrEmpty() -> profile?.profilePicUrl
            else -> null
        }
        initViews(userName, userImage)

        val chatBody = ChatBody.Builder()

        val current = PrefUtils.getProfileInfo()
        chatBody.receiver(userId)
            .sender(current?.userId)
            .chatUserId(userId)
            .userName(current?.firstName)
            .image(current?.profilePicUrl)
            .messageType(MessageType.Text)
            .setType(Message.Type.chat)

        mBinding.btnSend.setOnClickListener {
            val input = mBinding.inputMessage.text?.toString()
            if (input.isNullOrEmpty()) return@setOnClickListener
            viewModel.sendMessage(chatBody.message(input)).run {
                mBinding.inputMessage.text = null
            }
        }

        userId?.let {
            NotifyPreference.clearNotification(this, it)
            NotificationUtil(this).clearNotification(it.hashCode())
            viewModel.initChatManager(it)
        }
        initAdapter()
    }

    private fun initAdapter() {
        adapter = ChatAdapter(PrefUtils.getProfileInfo()?.userId, this)
        mBinding.recyclerView.adapter = adapter

        viewModel.getChatList { result ->
            when (result) {
                is Resource.Success -> result.data?.let {
                    adapter?.addToStart(it, true)
                    viewModel.resetUnreadCounts()
                }
                is Resource.Error -> result.message?.let { showToast(it) }
            }
        }
    }

    private fun initViews(name: String?, image: String?) {
        mBinding.userName.text = name
        mBinding.userImage.setUserImage(image)
    }

    override fun onResume() {
        super.onResume()
        userId?.let { ActivityPreference.setUserInChat(this, it) }
    }

    override fun onPause() {
        super.onPause()
        userId?.let { ActivityPreference.clearUserInChatKey(this, userId) }
    }
}