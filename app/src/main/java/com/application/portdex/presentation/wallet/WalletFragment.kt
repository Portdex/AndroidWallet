package com.application.portdex.presentation.wallet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.application.portdex.R
import com.application.portdex.adapters.WalletItemsAdapter
import com.application.portdex.data.local.LocalCategories.getWalletSection
import com.application.portdex.databinding.FragmentWalletBinding
import com.application.portdex.domain.models.WalletItem
import com.application.portdex.presentation.base.BaseFragment
import com.application.portdex.ui.CustomUi.getGridLabeledView
import com.application.portdex.ui.CustomUi.getViewAdapter

class WalletFragment : BaseFragment() {

    private lateinit var mBinding: FragmentWalletBinding

    companion object {
        fun newInstance() = WalletFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentWalletBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapters()
        mBinding.swipeContainer.isEnabled = false
    }

    private fun initAdapters() {
        activity?.getWalletSection()?.let { list ->
            attachSection(getString(R.string.label_coming_soon), list)
        }
        activity?.getWalletSection(false)?.let { list ->
            attachSection(getString(R.string.label_peer_to_peer), list)
        }
    }

    private fun attachSection(label: String, list: MutableList<WalletItem>) {
        mBinding.listContainer.getViewAdapter(label)?.let { adapter ->
            if (adapter is WalletItemsAdapter) adapter.addList(list)
        } ?: mBinding.listContainer.addView(getGridLabeledView(label, 3).apply {
            recyclerView.adapter = WalletItemsAdapter().apply {
                addList(list)
            }
        }.root)
    }

}