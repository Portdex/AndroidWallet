package com.application.portdex.presentation.home.homeMarket

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import com.application.portdex.R
import com.application.portdex.adapters.DesignsAdapter
import com.application.portdex.adapters.DigitalAdapter
import com.application.portdex.adapters.HomeSliderAdapter
import com.application.portdex.databinding.FragmentHomeMarketBinding
import com.application.portdex.domain.models.DigitalDesignItem
import com.application.portdex.domain.models.HomeSliderItem
import com.application.portdex.presentation.base.BaseFragment
import com.application.portdex.ui.CustomUi.getHorizontalLabeledView
import com.application.portdex.ui.CustomUi.getViewAdapter
import com.application.portdex.ui.snap.CustomSnapHelper

class HomeMarketFragment : BaseFragment() {

    private lateinit var mBinding: FragmentHomeMarketBinding

    companion object {
        fun newInstance() = HomeMarketFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentHomeMarketBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initSlider()
        initAdapters()
        mBinding.swipeContainer.isEnabled = false
    }

    private fun initSlider() {
        val adapter = HomeSliderAdapter()
        mBinding.sliderPager.adapter = adapter
        mBinding.pagerDots.setViewPager2(mBinding.sliderPager)

        val list = mutableListOf<HomeSliderItem>()
        list.add(HomeSliderItem(0, R.drawable.home_slider_two))
        list.add(HomeSliderItem(1, R.drawable.home_slider_two))
        list.add(HomeSliderItem(2, R.drawable.home_slider_two))
        adapter.addList(list)
    }

    private fun initAdapters() {
        attachDesignsSection()

        val nftList = mutableListOf<DigitalDesignItem>().apply {
            add(DigitalDesignItem(id = 0, "one"))
            add(DigitalDesignItem(id = 1, "two"))
            add(DigitalDesignItem(id = 2, "three"))
        }
        attachHorizontalSection(
            getString(R.string.label_digital_nft), nftList, R.layout.nft_slider_list_view, true
        )

        val themesList = mutableListOf<DigitalDesignItem>().apply {
            add(DigitalDesignItem(0, "Word press", "themes"))
            add(DigitalDesignItem(1, "Shopify", "themes"))
        }
        attachHorizontalSection(
            getString(R.string.label_themes), themesList, R.layout.themes_list_view
        )

        val buyNftList = mutableListOf<DigitalDesignItem>().apply {
            add(DigitalDesignItem(0, "Designs", "nft"))
            add(DigitalDesignItem(0, "Art", "nft"))
            add(DigitalDesignItem(0, "Photos", "nft"))
        }
        attachHorizontalSection(
            getString(R.string.label_buy_nft), buyNftList, R.layout.buy_nft_list_view
        )
    }

    private fun attachDesignsSection() {
        val data = mutableListOf<DigitalDesignItem>()
        data.add(DigitalDesignItem(0, "App Designs"))
        data.add(DigitalDesignItem(1, "Print Designs"))
        data.add(DigitalDesignItem(2, "App Designs"))
        val label = getString(R.string.label_digital_designs)
        mBinding.listContainer.getViewAdapter(label)?.let { adapter ->
            if (adapter is DesignsAdapter) adapter.addList(data)
        } ?: mBinding.listContainer.addView(getHorizontalLabeledView(label).apply {
            recyclerView.adapter = DesignsAdapter().apply { addList(data) }
        }.root)
    }


    private fun attachHorizontalSection(
        label: String,
        data: MutableList<DigitalDesignItem>,
        @LayoutRes resource: Int,
        isPaging: Boolean = false
    ) {
        mBinding.listContainer.getViewAdapter(label)?.let { adapter ->
            if (adapter is DigitalAdapter) adapter.addList(data)
        } ?: mBinding.listContainer.addView(getHorizontalLabeledView(label).apply {
            recyclerView.adapter = DigitalAdapter(resource = resource).apply { addList(data) }
            if (isPaging) CustomSnapHelper().attachToRecyclerView(recyclerView)
        }.root)
    }

}