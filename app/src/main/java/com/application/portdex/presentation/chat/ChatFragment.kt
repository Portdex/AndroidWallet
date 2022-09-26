package com.application.portdex.presentation.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.application.portdex.adapters.ChatTabsAdapter
import com.application.portdex.data.local.LocalCategories.getChatTabs
import com.application.portdex.databinding.FragmentChatBinding
import com.application.portdex.presentation.base.BaseFragment

class ChatFragment : BaseFragment() {

    private lateinit var mBinding: FragmentChatBinding

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
    }

    private fun initTabs() {
        val tabs = activity?.getChatTabs() ?: mutableListOf()
        val adapter = ChatTabsAdapter {}
        mBinding.tabLayout.adapter = adapter
        adapter.addList(tabs)

    }

}