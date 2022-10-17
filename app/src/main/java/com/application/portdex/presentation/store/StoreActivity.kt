package com.application.portdex.presentation.store

import android.os.Bundle
import androidx.activity.viewModels
import com.application.portdex.R
import com.application.portdex.adapters.ProviderTabsAdapter
import com.application.portdex.core.dataBinding.DataBinding.setUserImage
import com.application.portdex.data.local.LocalCategories
import com.application.portdex.databinding.StoreActivityBinding
import com.application.portdex.domain.models.ProfileInfo
import com.application.portdex.domain.models.ProviderInfo
import com.application.portdex.domain.models.category.CategoryData
import com.application.portdex.domain.viewmodels.StoreViewModel
import com.application.portdex.presentation.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StoreActivity : BaseActivity() {

    companion object {
        const val PROVIDER_ITEM = "StoreActivity.PROVIDER_ITEM"
        const val PROFILE_ITEM = "StoreActivity.PROFILE_ITEM"
    }

    //intent
    private val provider by lazy { intent?.getParcelableExtra<ProviderInfo>(PROVIDER_ITEM) }
    private val profile by lazy { intent?.getParcelableExtra<ProfileInfo>(PROFILE_ITEM) }

    private val storeViewModel by viewModels<StoreViewModel>()
    private lateinit var mBinding: StoreActivityBinding
    private var tabsAdapter = ProviderTabsAdapter(onTabSelect())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = StoreActivityBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        setSupportActionBar(mBinding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        initListeners()
        initData()
        initAdapter()
    }

    private fun initListeners() {
        mBinding.actionAdd.setOnClickListener {

        }
    }

    private fun initData() {
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
        val storeId = when {
            !provider?.storeId.isNullOrEmpty() -> provider?.storeId
            !profile?.storeId.isNullOrEmpty() -> profile?.storeId
            else -> null
        }
        mBinding.headerTitle.text = userName
        mBinding.userImage.setUserImage(userImage)
        mBinding.productCounts.text = getString(R.string.format_products, 0)

        storeId?.let { id ->
            // storeViewModel.getRetailerStoreProducts(id)
        }
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