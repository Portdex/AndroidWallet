package com.application.portdex.presentation.home.homeAll

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.core.view.setPadding
import androidx.fragment.app.activityViewModels
import com.application.portdex.R
import com.application.portdex.adapters.*
import com.application.portdex.data.local.LocalCategories.getThingsNearBy
import com.application.portdex.data.utils.Resource
import com.application.portdex.databinding.FragmentHomeAllBinding
import com.application.portdex.domain.models.DesignersItem
import com.application.portdex.domain.models.DoctorItem
import com.application.portdex.domain.models.HomeSliderItem
import com.application.portdex.domain.models.PropertyItem
import com.application.portdex.domain.models.category.CategoryItem
import com.application.portdex.domain.viewmodels.CategoriesViewModel
import com.application.portdex.presentation.base.BaseFragment
import com.application.portdex.ui.CustomUi.getFreelancerLabeledView
import com.application.portdex.ui.CustomUi.getGridLabeledView
import com.application.portdex.ui.CustomUi.getHorizontalLabeledView
import com.application.portdex.ui.CustomUi.getViewAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeAllFragment : BaseFragment() {

    private val viewModel: CategoriesViewModel by activityViewModels()
    private lateinit var mBinding: FragmentHomeAllBinding

    companion object {
        fun newInstance() = HomeAllFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentHomeAllBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initSlider()
        initThingsNearBy()
        mBinding.swipeContainer.setOnRefreshListener { loadData() }
        showProgress()
        loadData()
    }

    private fun loadData() {
        viewModel.getCategories { result ->
            when (result) {
                is Resource.Success -> result.data?.handleResult()
                is Resource.Error -> result.message?.let { showToast(it) }
            }
            mBinding.swipeContainer.isRefreshing = false
            hideProgress()
        }
    }

    private fun initSlider() {
        val adapter = HomeSliderAdapter()
        mBinding.sliderPager.adapter = adapter
        mBinding.pagerDots.setViewPager2(mBinding.sliderPager)

        val list = mutableListOf<HomeSliderItem>()
        list.add(HomeSliderItem(0, R.drawable.home_slider_one))
        list.add(HomeSliderItem(1, R.drawable.home_slider_one))
        list.add(HomeSliderItem(2, R.drawable.home_slider_one))
        adapter.addList(list)
    }

    private fun initThingsNearBy() {
        val list = activity?.getThingsNearBy() ?: emptyList()
        getGridLabeledView(getString(R.string.label_thing_nearby), space = 4).apply {
            recyclerView.adapter =
                HomeCategoriesAdapter(resource = R.layout.nearby_list_view) { category ->

                }.apply { addList(list) }
        }.also {
            mBinding.listContainer.addView(it.root)
        }
    }

    private fun List<CategoryItem>.handleResult() {
        find { it.id == "1" }?.let {
            attachGridSection(item = it, resource = R.layout.retailer_list_view, hasPadding = false)
        }
        find { it.id == "5" }?.let { attachServicesSection(it) {} }
        find { it.id == "3" }?.let {
            attachHorizontalSection(it, resource = R.layout.food_list_view)
        }
        attachDesignerSection()
        find { it.id == "2" }?.let { attachServicesSection(it, counts = 2) }
        find { it.id == "4" }?.let { attachDoctorsSection(it) }
        find { it.id == "7" }?.let { attachPropertySection(it) }
    }

    private fun attachGridSection(
        item: CategoryItem,
        @LayoutRes resource: Int,
        counts: Int = 3,
        hasPadding: Boolean = true
    ) {
        mBinding.listContainer.getViewAdapter(item.title)?.let { adapter ->
            if (adapter is HomeCategoriesAdapter) adapter.addList(item.data.sortedBy { it.id })
        } ?: mBinding.listContainer.addView(getGridLabeledView(item.title, counts).apply {
            if (!hasPadding) recyclerView.setPadding(0)
            recyclerView.adapter = HomeCategoriesAdapter(resource = resource).apply {
                addList(item.data.sortedBy { it.id })
            }
        }.root)
    }

    private fun attachHorizontalSection(item: CategoryItem, @LayoutRes resource: Int) {
        mBinding.listContainer.getViewAdapter(item.title)?.let { adapter ->
            if (adapter is HomeCategoriesAdapter) adapter.addList(item.data.sortedBy { it.id })
        } ?: mBinding.listContainer.addView(getHorizontalLabeledView(item.title).apply {
            recyclerView.adapter = HomeCategoriesAdapter(resource = resource).apply {
                addList(item.data.sortedBy { it.id })
            }
        }.root)
    }

    private fun attachServicesSection(
        item: CategoryItem,
        counts: Int = 3,
        connect: (() -> Unit)? = null
    ) {
        mBinding.listContainer.getViewAdapter(item.title)?.let { adapter ->
            if (adapter is HomeCategoriesAdapter) adapter.addList(item.data.sortedBy { it.id })
        } ?: mBinding.listContainer.addView(
            getFreelancerLabeledView(item.title, counts, connect).apply {
                recyclerView.adapter = HomeCategoriesAdapter(R.layout.services_list_view).apply {
                    addList(item.data.sortedBy { it.id })
                }
            }.root
        )
    }

    private fun attachDesignerSection() {
        val data = mutableListOf<DesignersItem>()
        data.add(DesignersItem(id = 0, userName = "Jordan Clark", rating = "4.0(50+ ratings)"))
        data.add(
            DesignersItem(id = 1, userName = "Philip", rating = "4.5(70+ ratings)", isActive = true)
        )
        data.add(DesignersItem(id = 2, userName = "Robert", rating = "3.0(90+ ratings)"))

        val label = getString(R.string.label_top_recommended_designers)
        mBinding.listContainer.getViewAdapter(label)?.let { adapter ->
            if (adapter is DesignerAdapter) adapter.addList(data)
        } ?: mBinding.listContainer.addView(getHorizontalLabeledView(label) {
            showToast("See All")
        }.apply {
            recyclerView.adapter = DesignerAdapter().apply {
                addList(data)
            }
        }.root)
    }

    private fun attachDoctorsSection(item: CategoryItem) {
        val data = item.data.map {
            DoctorItem(id = it.id, userName = it.name, occupation = "Dentist")
        }.toMutableList()
        mBinding.listContainer.getViewAdapter(item.title)?.let { adapter ->
            if (adapter is DoctorAdapter) adapter.addList(data)
        } ?: mBinding.listContainer.addView(getHorizontalLabeledView(item.title).apply {
            recyclerView.adapter = DoctorAdapter().apply {
                addList(data)
            }
        }.root)
    }

    private fun attachPropertySection(item: CategoryItem) {
        val data = item.data.map {
            PropertyItem(id = it.id, userName = it.name, designation = "CEO / General manager")
        }.toMutableList()
        mBinding.listContainer.getViewAdapter(item.title)?.let { adapter ->
            if (adapter is PropertyAdapter) adapter.addList(data)
        } ?: mBinding.listContainer.addView(getHorizontalLabeledView(item.title).apply {
            recyclerView.adapter = PropertyAdapter().apply {
                addList(data)
            }
        }.root)
    }
}