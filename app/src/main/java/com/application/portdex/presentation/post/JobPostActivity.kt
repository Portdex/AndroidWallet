package com.application.portdex.presentation.post

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import com.application.portdex.R
import com.application.portdex.adapters.CategoryCheckedAdapter
import com.application.portdex.core.utils.GenericUtils.textSpaceWatcher
import com.application.portdex.core.utils.ValidationUtils.getValidString
import com.application.portdex.data.local.LocalCategories.getThingsNearBy
import com.application.portdex.data.utils.Resource
import com.application.portdex.databinding.JobPostActivityBinding
import com.application.portdex.domain.models.category.CategoryData
import com.application.portdex.domain.models.category.CategoryItem
import com.application.portdex.domain.models.post.CreatePost
import com.application.portdex.domain.viewmodels.CategoriesViewModel
import com.application.portdex.domain.viewmodels.PostViewModel
import com.application.portdex.presentation.base.BaseActivity
import com.application.portdex.ui.CustomUi.getGridLabeledView
import com.jacopo.pagury.prefs.PrefUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class JobPostActivity : BaseActivity() {

    private val postViewModel by viewModels<PostViewModel>()
    private val categoriesViewModel: CategoriesViewModel by viewModels()

    private lateinit var mBinding: JobPostActivityBinding

    private var mCategory: CategoryData? = null
    private var subCategory: CategoryData? = null

    private var subCategoriesAdapter: CategoryCheckedAdapter? = null
    private var subCategoriesList = listOf<CategoryItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = JobPostActivityBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        setSupportActionBar(mBinding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        initAdapters()
        initCurrencies()
        initViews()
    }

    private fun initViews() {
        mBinding.inputName.textSpaceWatcher()
        mBinding.inputAbout.textSpaceWatcher()
        mBinding.inputBudget.textSpaceWatcher()

        mBinding.btnCreate.setOnClickListener { doOnCreate() }
    }

    private fun doOnCreate() {
        val projectName = mBinding.inputName.getValidString()
        val projectAbout = mBinding.inputAbout.getValidString()
        val budget = mBinding.inputBudget.getValidString()
        val currency = mBinding.inputCurrency.getValidString()

        when {
            projectName.isNullOrEmpty() -> {
                mBinding.inputNameLayout.apply {
                    error = getString(R.string.error_input_required)
                    requestFocus()
                }
            }
            projectAbout.isNullOrEmpty() -> {
                mBinding.inputAboutLayout.apply {
                    error = getString(R.string.error_input_required)
                    requestFocus()
                }
            }
            budget.isNullOrEmpty() -> {
                mBinding.inputBudgetLayout.apply {
                    error = getString(R.string.error_input_required)
                    requestFocus()
                }
            }
            currency.isNullOrEmpty() -> {
                mBinding.inputCurrencyLayout.apply {
                    error = getString(R.string.error_input_required)
                    requestFocus()
                }
            }
        }

        val profileInfo = PrefUtils.getProfileInfo()
        val location = PrefUtils.getLocation()
        val post = CreatePost(
            phoneNumber = profileInfo?.phoneNumber,
            userId = profileInfo?.userId,
            category = mCategory?.title,
            subCategory = subCategory?.name,
            postSubject = projectName,
            postDescription = projectAbout,
            lat = location?.latitude?.toString(),
            long = location?.longitude?.toString()
        )

        showProgress()
        postViewModel.createPost(post) { resource ->
            hideProgress()
            when (resource) {
                is Resource.Success -> if (resource.data == true) {
                    showToast(R.string.info_post_created)
                    onBackPressed()
                }
                is Resource.Error -> resource.message?.let { showToast(it) }
            }
        }
    }

    private fun initCurrencies() {
        val currencies = resources.getStringArray(R.array.currencies)
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, currencies)
        mBinding.inputCurrency.setAdapter(adapter)
    }

    private fun initAdapters() {
        val ignored = listOf(0, 6, 8, 9)
        val nearbyList = getThingsNearBy().filterNot { item ->
            ignored.any { item.id == it }
        }.toMutableList()
        mCategory = nearbyList.firstOrNull()
        getGridLabeledView(getString(R.string.label_categories), space = 4).apply {
            recyclerView.adapter =
                CategoryCheckedAdapter(resource = R.layout.nearby_list_view) { category ->
                    mCategory = category
                    onSelectCategory(category.id)
                }.apply { addList(nearbyList) }
        }.also { mBinding.listContainer.addView(it.root) }

        initSubcategories()
    }

    private fun initSubcategories() {
        getGridLabeledView(getString(R.string.label_sub_categories)).apply {
            subCategoriesAdapter = CategoryCheckedAdapter(
                resource = R.layout.services_list_view, hasImage = false
            ) { category -> subCategory = category }
            recyclerView.adapter = subCategoriesAdapter
        }.also { mBinding.listContainer.addView(it.root) }


        showProgress()
        categoriesViewModel.getCategories { result ->
            when (result) {
                is Resource.Success -> result.data?.let {
                    subCategoriesList = it
                    onSelectCategory(2)
                }
                is Resource.Error -> result.message?.let { showToast(it) }
            }
            hideProgress()
        }
    }

    private fun onSelectCategory(id: Int) {
        subCategoriesList.find { it.id == id.toString() }?.let { category ->
            subCategory = category.data.firstOrNull()
            subCategoriesAdapter?.addList(category.data.toMutableList())
        }
    }

}