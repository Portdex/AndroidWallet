package com.application.portdex.presentation.chat.activity

import android.os.Bundle
import androidx.activity.viewModels
import com.application.portdex.core.dataBinding.DataBinding.setUserImage
import com.application.portdex.databinding.ChatActivityBinding
import com.application.portdex.domain.models.ProviderInfo
import com.application.portdex.domain.viewmodels.ChatViewModel
import com.application.portdex.presentation.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChatActivity : BaseActivity() {

    companion object {
        const val PROVIDER_ITEM = "ChatActivity.PROVIDER_ITEM"
        const val PROFILE_ITEM = "ChatActivity.PROFILE_ITEM"
    }

    private val viewModel by viewModels<ChatViewModel>()
    private lateinit var mBinding: ChatActivityBinding

    //intent
    private val provider by lazy { intent?.getParcelableExtra<ProviderInfo>(PROVIDER_ITEM) }
    private val profile by lazy { intent?.getParcelableExtra<ProviderInfo>(PROFILE_ITEM) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ChatActivityBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        initIntent()
    }

    private fun initIntent() {
        provider?.let { initViews(it.firstName, it.profilePicUrl) }
        profile?.let { initViews(it.firstName, it.profilePicUrl) }
    }

    private fun initViews(name: String?, image: String?) {
        mBinding.userName.text = name
        mBinding.userImage.setUserImage(image)
    }
}