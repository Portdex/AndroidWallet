package com.application.portdex.presentation.login.business

import android.os.Bundle
import androidx.activity.viewModels
import com.application.portdex.R
import com.application.portdex.adapters.CategoryCheckedAdapter
import com.application.portdex.core.filePicker.FilePickerImpl
import com.application.portdex.data.local.LocalCategories.getThingsNearBy
import com.application.portdex.data.utils.Resource
import com.application.portdex.databinding.BusinessActivityBinding
import com.application.portdex.domain.models.category.CategoryItem
import com.application.portdex.domain.viewmodels.CategoriesViewModel
import com.application.portdex.presentation.base.BaseActivity
import com.application.portdex.ui.CustomUi.getGridLabeledView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginBusinessActivity : BaseActivity() {

    private val filePicker = FilePickerImpl()
    private val categoriesViewModel: CategoriesViewModel by viewModels()
    private lateinit var mBinding: BusinessActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = BusinessActivityBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        setSupportActionBar(mBinding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        filePicker.initPicker(this)
        initListeners()
        initAdapters()
    }

    private fun initAdapters() {
        getGridLabeledView(getString(R.string.label_categories), space = 4).apply {
            recyclerView.adapter =
                CategoryCheckedAdapter(resource = R.layout.nearby_list_view) { category ->

                }.apply { addList(getThingsNearBy().toMutableList()) }
        }.also { mBinding.listContainer.addView(it.root) }

        showProgress()
        categoriesViewModel.getCategories { result ->
            when (result) {
                is Resource.Success -> result.data?.handleResult()
                is Resource.Error -> result.message?.let { showToast(it) }
            }
            hideProgress()
        }
    }

    private fun List<CategoryItem>.handleResult() {
        find { it.id == "5" }?.let { category ->
            getGridLabeledView(getString(R.string.label_sub_categories)).apply {
                recyclerView.adapter =
                    CategoryCheckedAdapter(resource = R.layout.services_list_view) { category ->

                    }.apply { addList(category.data.toMutableList()) }
            }.also { mBinding.listContainer.addView(it.root) }
        }
    }

    private fun initListeners() {
        mBinding.btnUploadImage.setOnClickListener { filePicker.pickImage() }
        filePicker.setPickImageListener { uri ->
            mBinding.imageView.setImageURI(uri)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        filePicker.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}