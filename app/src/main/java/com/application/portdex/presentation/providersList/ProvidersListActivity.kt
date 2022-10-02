package com.application.portdex.presentation.providersList

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.application.portdex.adapters.ProviderTabsAdapter
import com.application.portdex.adapters.ServiceProviderAdapter
import com.application.portdex.core.utils.GenericUtils.show
import com.application.portdex.data.utils.Resource
import com.application.portdex.databinding.ProviderListActivityBinding
import com.application.portdex.domain.models.ProviderInfo
import com.application.portdex.domain.models.category.CategoryData
import com.application.portdex.domain.models.category.CategoryItem
import com.application.portdex.domain.viewmodels.CategoriesViewModel
import com.application.portdex.presentation.base.BaseActivity
import com.application.portdex.presentation.providersList.detail.ProviderDetailActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProvidersListActivity : BaseActivity() {

    companion object {
        const val PROVIDER_TYPE = "ProvidersListActivity.PROVIDER_TYPE"
    }

    private val provider by lazy { intent?.getParcelableExtra<CategoryData>(PROVIDER_TYPE) }

    private val viewModel by viewModels<CategoriesViewModel>()
    private lateinit var mBinding: ProviderListActivityBinding

    private var tabsAdapter = ProviderTabsAdapter(onTabSelect())
    private var providersAdapter = ServiceProviderAdapter { provider ->
        openProviderDetail(provider)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ProviderListActivityBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        setSupportActionBar(mBinding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        mBinding.title.text = provider?.name

        initTabs()
    }

    private fun initTabs() {
        mBinding.tabLayout.adapter = tabsAdapter

        providersAdapter.emptyResultListener = { isEmpty ->
            mBinding.emptyView.show(isEmpty)
        }
        mBinding.recyclerView.adapter = providersAdapter

        showProgress()
        viewModel.getCategories { result ->
            when (result) {
                is Resource.Success -> result.data?.handleResult()
                is Resource.Error -> result.message?.let {
                    hideProgress()
                    showToast(it)
                }
            }
        }
    }

    private fun onTabSelect(): (CategoryData) -> Unit {
        return { tab ->
            mBinding.providerLabel.text = tab.name
            providersAdapter.filter.filter(tab.name)
        }
    }

    private fun List<CategoryItem>.handleResult() {
        find { it.id == provider?.id?.toString() }?.let { item ->
            tabsAdapter.addList(item.data.toMutableList())
            mBinding.divider.show(item.data.isNotEmpty())
            mBinding.emptyView.show(item.data.isEmpty())

            provider?.title?.let { getProvideData(it) }

            item.data.firstOrNull()?.let { category ->
                mBinding.providerLabel.text = category.name
            }
        } ?: kotlin.run {
            mBinding.emptyView.show()
            hideProgress()
        }
    }

    private fun getProvideData(category: String) {
        viewModel.getProfileByCategory(category) { result ->
            when (result) {
                is Resource.Success -> result.data?.let {
                    providersAdapter.addList(it.toMutableList())
                }
                is Resource.Error -> result.message?.let { showToast(it) }
            }
            hideProgress()
        }
    }

    private fun openProviderDetail(provider: ProviderInfo) {
        startWithAnim(Intent(this, ProviderDetailActivity::class.java).apply {
            putExtra(ProviderDetailActivity.PROVIDER_INFO, provider)
        })
    }
}