package com.application.portdex.presentation.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.application.portdex.adapters.ChatTabsAdapter
import com.application.portdex.adapters.chat.ThreadsAdapter
import com.application.portdex.data.local.LocalCategories.getChatTabs
import com.application.portdex.data.utils.Resource
import com.application.portdex.databinding.FragmentChatBinding
import com.application.portdex.domain.models.ProfileInfo
import com.application.portdex.domain.models.chat.Threads
import com.application.portdex.domain.viewmodels.ChatViewModel
import com.application.portdex.presentation.base.BaseFragment
import com.application.portdex.presentation.chat.activity.ChatActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChatFragment : BaseFragment() {

    private val chatViewModel by activityViewModels<ChatViewModel>()
    private lateinit var mBinding: FragmentChatBinding
    private var adapter = ThreadsAdapter { threads -> startChatActivity(threads) }

    companion object {
        fun newInstance() = ChatFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentChatBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initTabs()
        initAdapter()
    }

    private fun initAdapter() {
        mBinding.recyclerView.addItemDecoration(
            DividerItemDecoration(requireContext(), RecyclerView.VERTICAL)
        )
        mBinding.recyclerView.adapter = adapter

        chatViewModel.getThreads { result ->
            when (result) {
                is Resource.Success -> result.data?.let {
                    adapter.setList(it.toMutableList())
                }
                is Resource.Error -> result.message?.let { showToast(it) }
            }
        }
    }

    private fun startChatActivity(thread: Threads) {
        startChatActivity(Bundle().apply {
            putParcelable(
                ChatActivity.PROFILE_ITEM, ProfileInfo(
                    userId = thread.chatUserId,
                    firstName = thread.userName,
                    profilePicUrl = thread.userImage
                )
            )
        })
    }

    private fun initTabs() {
        val tabs = activity?.getChatTabs() ?: mutableListOf()
        val adapter = ChatTabsAdapter {}
        mBinding.tabLayout.adapter = adapter
        adapter.addList(tabs)

    }

}