package com.application.portdex.presentation.login.business

import android.net.Uri
import android.os.Bundle
import androidx.activity.viewModels
import com.anggrayudi.storage.file.DocumentFileCompat
import com.application.portdex.R
import com.application.portdex.adapters.CategoryCheckedAdapter
import com.application.portdex.core.filePicker.FilePickerImpl
import com.application.portdex.core.utils.GenericUtils.getCountry
import com.application.portdex.core.utils.GenericUtils.getUserLocation
import com.application.portdex.core.utils.ValidationUtils.getValidString
import com.application.portdex.data.local.LocalCategories.getThingsNearBy
import com.application.portdex.data.utils.Resource
import com.application.portdex.databinding.BusinessActivityBinding
import com.application.portdex.domain.models.CreateProfileInfo
import com.application.portdex.domain.models.ProfileInfo
import com.application.portdex.domain.models.category.CategoryData
import com.application.portdex.domain.models.category.CategoryItem
import com.application.portdex.domain.models.store.StoreInfo
import com.application.portdex.domain.viewmodels.CategoriesViewModel
import com.application.portdex.domain.viewmodels.ProfileViewModel
import com.application.portdex.domain.viewmodels.StoreViewModel
import com.application.portdex.presentation.base.BaseActivity
import com.application.portdex.ui.CustomUi.getGridLabeledView
import com.jacopo.pagury.prefs.PrefUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginBusinessActivity : BaseActivity() {

    companion object {
        const val PROFILE_KEY = "LoginBusinessActivity.PROFILE_KEY"
        const val PROFILE_IMAGE_KEY = "LoginBusinessActivity.PROFILE_IMAGE_KEY"
    }

    private val profileViewModel by viewModels<ProfileViewModel>()
    private val storeViewModel by viewModels<StoreViewModel>()
    private val categoriesViewModel: CategoriesViewModel by viewModels()


    private lateinit var mBinding: BusinessActivityBinding
    private val filePicker = FilePickerImpl()

    private val profileInfo by lazy { intent?.getParcelableExtra<CreateProfileInfo>(PROFILE_KEY) }
    private val profileImage by lazy { intent?.getParcelableExtra<Uri>(PROFILE_IMAGE_KEY) }

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
        initAdapters()
    }

    private fun initAdapters() {
        val nearbyList = getThingsNearBy().toMutableList()
        mCategory = nearbyList.firstOrNull()
        getGridLabeledView(getString(R.string.label_categories), space = 4).apply {
            recyclerView.adapter =
                CategoryCheckedAdapter(resource = R.layout.nearby_list_view) { category ->
                    mCategory = category
                }.apply { addList(nearbyList) }
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
            subCategory = category.data.firstOrNull()
            getGridLabeledView(getString(R.string.label_sub_categories)).apply {
                recyclerView.adapter =
                    CategoryCheckedAdapter(resource = R.layout.services_list_view) { category ->
                        subCategory = category
                    }.apply { addList(category.data.toMutableList()) }
            }.also { mBinding.listContainer.addView(it.root) }
        }
    }

    private fun initListeners() {
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
            else -> {
                val imageFile = imageUri?.let { DocumentFileCompat.fromUri(this, it) }
                val location = PrefUtils.getLocation()
                val store = StoreInfo(
                    storePhone = profileInfo?.phoneNo,
                    storeName = name,
                    category = mCategory?.title,
                    subCategory = subCategory?.title,
                    lat = location?.latitude?.toString(),
                    long = location?.longitude?.toString(),
                    storeAddress = getUserLocation(),
                    country = getCountry()
                )
                createProfile { profile ->
                    store.userId = profile.userId
                    storeViewModel.createStore(store, imageFile) { it.onStoreCreated() }
                }
            }
        }
    }

    private fun Resource<StoreInfo>.onStoreCreated() {
        hideProgress()
        when (this) {
            is Resource.Success -> startMainActivity()
            is Resource.Error -> message?.let {
                showToast(it)
                setResult(RESULT_CANCELED)
                onBackPressed()
            }
        }
    }

    private fun createProfile(listener: (ProfileInfo) -> Unit) {
        val imageFile = profileImage?.let { DocumentFileCompat.fromUri(this, it) }
        profileInfo?.let { profile ->
            showProgress()
            profileViewModel.createProfile(profile, imageFile) { resource ->
                when (resource) {
                    is Resource.Success -> resource.data?.let { profile ->
                        PrefUtils.setProfileInfo(profile)
                        listener(profile)
                    }
                    is Resource.Error -> resource.message?.let {
                        showToast(it)
                        hideProgress()
                    }
                }
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        filePicker.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}