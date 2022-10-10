package com.application.portdex.presentation.providersList.detail

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.application.portdex.adapters.ProvidePackagesAdapter
import com.application.portdex.core.utils.GenericUtils.hide
import com.application.portdex.core.utils.GenericUtils.show
import com.application.portdex.core.utils.ResultContract
import com.application.portdex.data.utils.Resource
import com.application.portdex.databinding.ProviderDetailActivityBinding
import com.application.portdex.domain.models.ProviderInfo
import com.application.portdex.domain.models.ProviderPackage
import com.application.portdex.domain.viewmodels.CategoriesViewModel
import com.application.portdex.domain.viewmodels.StoreViewModel
import com.application.portdex.presentation.base.BaseActivity
import com.application.portdex.presentation.cart.CartActivity
import com.application.portdex.presentation.chat.activity.ChatActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.abs

@AndroidEntryPoint
class ProviderDetailActivity : BaseActivity() {

    companion object {
        const val PROVIDER_INFO = "ProviderDetailActivity.PROVIDER_INFO"
    }

    private val storeViewModel by viewModels<StoreViewModel>()
    private val viewModel by viewModels<CategoriesViewModel>()
    private lateinit var mBinding: ProviderDetailActivityBinding
    private val adapter = ProvidePackagesAdapter()
    private var cartItems = listOf<ProviderPackage>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ProviderDetailActivityBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        setSupportActionBar(mBinding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        mBinding.lifecycleOwner = this
        initCartItems()
        initAdapter()
        intent?.getParcelableExtra<ProviderInfo>(PROVIDER_INFO)?.let { provider ->
            mBinding.provider = provider
            provider.userId?.let { userId -> getPackagesDetail(userId) }
            initProvider(provider)
        }
        mBinding.appbar.addOnOffsetChangedListener { appBar, verticalOffset ->
            mBinding.userImage.hide(abs(verticalOffset) >= appBar.totalScrollRange)

        }
    }

    private fun initProvider(provider: ProviderInfo) {
        mBinding.btnChat.setOnClickListener {
            startChatActivity(Bundle().apply {
                putParcelable(ChatActivity.PROVIDER_ITEM, provider)
            })
        }
    }

    private fun initAdapter() {
        adapter.cartItemListener = { item ->
            if (item.isCartItem) storeViewModel.deleteCartItem(item) {
                it.handleCartResult(item, false)
            }
            else storeViewModel.insertIntoCart(item) { it.handleCartResult(item, true) }
        }
        mBinding.recyclerView.adapter = adapter
    }

    private fun initCartItems() {
        storeViewModel.getCartItems { resource ->
            when (resource) {
                is Resource.Success -> resource.data?.let {
                    cartItems = it
                    mBinding.cartCounts.apply {
                        text = it.size.toString()
                        show(it.isNotEmpty())
                    }
                    if (it.isNotEmpty()) mBinding.actionCart.setOnClickListener {
                        startWithAnim { cartResult.launch(Intent(this, CartActivity::class.java)) }
                    }
                }
                is Resource.Error -> resource.message?.let { showToast(it) }
            }
        }
    }

    private val cartResult = registerForActivityResult(ResultContract()) { intent ->
        val itemRemoved = intent?.getBooleanExtra(CartActivity.ITEM_REMOVED, false)
        if (itemRemoved == true) {
            intent.getStringArrayListExtra(CartActivity.REMOVED_ITEMS)?.let { list ->
                adapter.removedCartItems(list)
            }
        }
    }

    private fun Resource<Boolean>.handleCartResult(item: ProviderPackage, status: Boolean) {
        when (this) {
            is Resource.Success -> {
                item.isCartItem = status
                adapter.updateItem(item)
            }
            is Resource.Error -> message?.let { showToast(it) }
        }
    }

    private fun getPackagesDetail(userId: String) {
        showProgress()
        viewModel.getProviderPackages(userId) { result ->
            when (result) {
                is Resource.Success -> result.data?.let { list ->
                    val filtered = list.map { item ->
                        item.apply { isCartItem = cartItems.any { it.packageId == item.packageId } }
                    }.toMutableList()
                    adapter.addList(filtered)
                }
                is Resource.Error -> result.message?.let { showToast(it) }
            }
            hideProgress()
        }
    }
}