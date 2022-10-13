package com.application.portdex.presentation.login.business

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.viewModels
import com.application.portdex.R
import com.application.portdex.adapters.CategoryCheckedAdapter
import com.application.portdex.core.filePicker.FilePickerImpl
import com.application.portdex.core.utils.GenericUtils
import com.application.portdex.core.utils.GenericUtils.getCountry
import com.application.portdex.core.utils.GenericUtils.getUserLocation
import com.application.portdex.core.utils.GenericUtils.show
import com.application.portdex.core.utils.ValidationUtils.getValidString
import com.application.portdex.core.utils.ValidationUtils.isSuccess
import com.application.portdex.data.local.LocalCategories.getThingsNearBy
import com.application.portdex.data.utils.Resource
import com.application.portdex.databinding.BusinessActivityBinding
import com.application.portdex.domain.models.CreateProfileInfo
import com.application.portdex.domain.models.category.CategoryData
import com.application.portdex.domain.models.category.CategoryItem
import com.application.portdex.domain.models.store.StoreInfo
import com.application.portdex.domain.viewmodels.CategoriesViewModel
import com.application.portdex.presentation.base.BaseActivity
import com.application.portdex.presentation.login.LoginViewModel
import com.application.portdex.presentation.login.verify.VerifyActivity
import com.application.portdex.ui.CustomUi.getGridLabeledView
import com.jacopo.pagury.prefs.PrefUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginBusinessActivity : BaseActivity() {

    companion object {
        const val INPUT_NUMBER = "LoginBusinessActivity.INPUT_NUMBER"
        const val IS_BUSINESS = "LoginBusinessActivity.IS_BUSINESS"
    }

    private val viewModel by viewModels<LoginViewModel>()
    private val categoriesViewModel: CategoriesViewModel by viewModels()


    private lateinit var mBinding: BusinessActivityBinding
    private val filePicker = FilePickerImpl()

    private val isBusiness by lazy {
        intent?.getBooleanExtra(IS_BUSINESS, false) ?: false
    }
    private val number by lazy { intent?.getStringExtra(INPUT_NUMBER) }

    private var subCategoriesAdapter: CategoryCheckedAdapter? = null
    private var subCategoriesList = listOf<CategoryItem>()

    private var mCategory: CategoryData? = null
    private var subCategory: CategoryData? = null
    private var imageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = BusinessActivityBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        setSupportActionBar(mBinding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        filePicker.initPicker(this)
        initListeners()
        if (isBusiness) initAdapters()
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

    private fun initListeners() {
        mBinding.listContainer.show(isBusiness)
        mBinding.btnUploadImage.setOnClickListener { filePicker.pickImage() }
        filePicker.setPickImageListener { uri ->
            imageUri = uri
            mBinding.imageView.setImageURI(uri)
        }
        mBinding.btnLogin.setOnClickListener { initLogin() }
    }

    private fun initLogin() {
        val name = mBinding.inputName.getValidString()
        when {
            name.isNullOrEmpty() -> mBinding.inputNameLayout.apply {
                error = getString(R.string.error_input_required)
                requestFocus()
            }
            else -> loginWithNumber(name)
        }
    }

    private fun loginWithNumber(name: String) {
        showProgress()
        number?.let { number ->
            viewModel.login(number) { resource ->
                hideProgress()
                when (resource) {
                    is Resource.Success -> if (resource.data.isSuccess()) startVerification(name)
                    is Resource.Error -> resource.message?.let { showToast(it) }
                }
            }
        }
    }

    private fun startVerification(name: String) {
        startWithAnim(Intent(this, VerifyActivity::class.java).apply {
            putExtra(VerifyActivity.INPUT_PROFILE, getProfile(name))
            if (isBusiness) {
                putExtra(VerifyActivity.INPUT_STORE, getStore(name))
            }
        })
    }

    private fun getProfile(name: String): CreateProfileInfo {
        val location = PrefUtils.getLocation()
        return CreateProfileInfo(
            phoneNo = number,
            firstName = name,
            latitude = location?.latitude?.toString(),
            longitude = location?.longitude?.toString(),
            country = getCountry(),
            category = "User",
            userToken = GenericUtils.getDeviceID(),
            signedUpUser = "1",
            imageUri = imageUri
        )
    }

    private fun getStore(name: String): StoreInfo {
        val location = PrefUtils.getLocation()
        return StoreInfo(
            storePhone = number,
            storeName = name,
            category = mCategory?.title,
            subCategory = subCategory?.title,
            lat = location?.latitude?.toString(),
            long = location?.longitude?.toString(),
            storeAddress = getUserLocation(),
            country = getCountry(),
            signedUpUser = "1",
            imageUri = imageUri
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        filePicker.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}