package com.application.portdex.presentation.wallet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.application.portdex.R
import com.application.portdex.adapters.MyPortfolioAdapter
import com.application.portdex.adapters.TransactionAdapter
import com.application.portdex.databinding.FragmentWalletBinding
import com.application.portdex.databinding.FragmentWalletNewBinding
import com.application.portdex.presentation.base.BaseFragment
import com.kds.cryptodesign.WatchListAdapter

class WalletFragmentNew :  BaseFragment() {
    private lateinit var mBinding: FragmentWalletNewBinding
    companion object {
        fun newInstance() = WalletFragmentNew()
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentWalletNewBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerViewPortPolio =view. findViewById<RecyclerView>(R.id.recycler_portPolio)
        val recyclerViewWatchList = view.findViewById<RecyclerView>(R.id.recycler_watchList)
        val recyclerViewTransition = view.findViewById<RecyclerView>(R.id.recycler_Transition)

        recyclerViewPortPolio.adapter = MyPortfolioAdapter(requireActivity())

        recyclerViewWatchList.adapter = WatchListAdapter(requireActivity())

        recyclerViewTransition.adapter = TransactionAdapter(requireActivity())

    }

}