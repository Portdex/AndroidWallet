package com.application.portdex.presentation.providersList.detail

import android.os.Bundle
import androidx.activity.viewModels
import com.application.portdex.adapters.ProvidePackagesAdapter
import com.application.portdex.data.utils.Resource
import com.application.portdex.databinding.ProviderDetailActivityBinding
import com.application.portdex.domain.models.ProviderInfo
import com.application.portdex.domain.viewmodels.CategoriesViewModel
import com.application.portdex.presentation.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProviderDetailActivity : BaseActivity() {

    companion object {
        const val PROVIDER_INFO = "ProviderDetailActivity.PROVIDER_INFO"
    }

    private val viewModel by viewModels<CategoriesViewModel>()
    private lateinit var mBinding: ProviderDetailActivityBinding
    private val adapter = ProvidePackagesAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ProviderDetailActivityBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        setSupportActionBar(mBinding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        mBinding.lifecycleOwner = this

        initAdapter()
        intent?.getParcelableExtra<ProviderInfo>(PROVIDER_INFO)?.let { provider ->
            mBinding.provider = provider
            provider.userId?.let { userId -> getPackagesDetail(userId) }
        }
    }

    private fun initAdapter() {
        mBinding.recyclerView.adapter = adapter
    }

    private fun getPackagesDetail(userId: String) {
        showProgress()
        viewModel.getProviderPackages(userId) { result ->
            when (result) {
                is Resource.Success -> result.data?.let { adapter.addList(it.toMutableList()) }
                is Resource.Error -> result.message?.let { showToast(it) }
            }
            hideProgress()
        }
    }
}