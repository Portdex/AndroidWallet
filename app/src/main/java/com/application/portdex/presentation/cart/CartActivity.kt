package com.application.portdex.presentation.cart

import android.os.Bundle
import androidx.activity.viewModels
import com.application.portdex.adapters.CartAdapter
import com.application.portdex.data.utils.Resource
import com.application.portdex.databinding.CartActivityBinding
import com.application.portdex.domain.models.ProviderPackage
import com.application.portdex.domain.viewmodels.StoreViewModel
import com.application.portdex.presentation.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartActivity : BaseActivity() {

    companion object {
        const val ITEM_REMOVED = "CartActivity.ITEM_REMOVED"
        const val REMOVED_ITEMS = "CartActivity.REMOVED_ITEMS"
    }

    private val viewModel by viewModels<StoreViewModel>()
    private lateinit var mBinding: CartActivityBinding
    private val adapter = CartAdapter { item -> deleteCartItem(item) }
    private val itemsList = arrayListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = CartActivityBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        setSupportActionBar(mBinding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        initAdapter()
    }

    private fun initAdapter() {
        mBinding.recyclerView.adapter = adapter

        viewModel.getCartItems { resource ->
            when (resource) {
                is Resource.Success -> resource.data?.let { list ->
                    adapter.addList(list.toMutableList())
                }
                is Resource.Error -> resource.message?.let { showToast(it) }
            }
        }
    }

    private fun deleteCartItem(item: ProviderPackage) {
        viewModel.deleteCartItem(item) { resource ->
            when (resource) {
                is Resource.Success -> if (resource.data == true) {
                    item.packageId?.let { itemsList.add(it) }
                    setResult(RESULT_OK, intent.apply {
                        putExtra(ITEM_REMOVED, true)
                        putStringArrayListExtra(REMOVED_ITEMS, itemsList)
                    })
                }
                is Resource.Error -> resource.message?.let { showToast(it) }
            }
        }
    }
}