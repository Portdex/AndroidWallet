package com.application.portdex.presentation.store

import android.os.Bundle
import com.application.portdex.adapters.ProviderTabsAdapter
import com.application.portdex.data.local.LocalCategories
import com.application.portdex.databinding.StoreActivityBinding
import com.application.portdex.domain.models.category.CategoryData
import com.application.portdex.presentation.base.BaseActivity

class StoreActivity : BaseActivity() {

    private lateinit var mBinding: StoreActivityBinding
    private var tabsAdapter = ProviderTabsAdapter(onTabSelect())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = StoreActivityBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        setSupportActionBar(mBinding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        initAdapter()
    }

    private fun initAdapter() {
        mBinding.tabLayout.adapter = tabsAdapter
        tabsAdapter.addList(LocalCategories.getRetailerProducts())
    }


    private fun onTabSelect(): (CategoryData) -> Unit {
        return { tab ->

        }
    }
}