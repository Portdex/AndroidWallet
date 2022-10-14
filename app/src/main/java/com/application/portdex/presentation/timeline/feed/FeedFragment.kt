package com.application.portdex.presentation.timeline.feed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.application.portdex.adapters.FeedsAdapter
import com.application.portdex.data.utils.Resource
import com.application.portdex.databinding.FeedFragmentBinding
import com.application.portdex.domain.viewmodels.CategoriesViewModel
import com.application.portdex.presentation.base.BaseFragment
import com.jacopo.pagury.prefs.PrefUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FeedFragment : BaseFragment() {

    private val viewModel by viewModels<CategoriesViewModel>()
    private lateinit var mBinding: FeedFragmentBinding
    private val adapter = FeedsAdapter()
    private var isMine = false

    companion object {
        fun newInstance(isMine: Boolean) = FeedFragment().apply {
            this.isMine = isMine
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        mBinding = FeedFragmentBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showProgress()
        initAdapter()
        loadData()
    }

    private fun initAdapter() {
        mBinding.recyclerView.adapter = adapter
        mBinding.swipeContainer.setOnRefreshListener { loadData() }
    }

    private fun loadData() {
        val userId = PrefUtils.getProfileInfo()?.userId
        viewModel.getNewsFeedPost { result ->
            when (result) {
                is Resource.Success -> result.data?.let { list ->
                    val formatted = list.sortedByDescending { it.createdDateTime }
                    val posts = if (isMine) formatted.filter { it.userId == userId } else formatted
                    adapter.setData(posts.toMutableList())
                }
                is Resource.Error -> result.message?.let { showToast(it) }
            }
            hideProgress()
            mBinding.swipeContainer.isRefreshing = false
        }
    }

}