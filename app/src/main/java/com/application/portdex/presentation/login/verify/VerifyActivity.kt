package com.application.portdex.presentation.login.verify

import android.os.Bundle
import androidx.activity.viewModels
import com.anggrayudi.storage.file.DocumentFileCompat
import com.application.portdex.R
import com.application.portdex.core.utils.ValidationUtils.getValidString
import com.application.portdex.core.utils.ValidationUtils.isProfileValid
import com.application.portdex.data.utils.Resource
import com.application.portdex.databinding.VerifyActivityBinding
import com.application.portdex.domain.models.CreateProfileInfo
import com.application.portdex.domain.models.LoginInfo
import com.application.portdex.domain.models.ProfileInfo
import com.application.portdex.domain.models.store.StoreInfo
import com.application.portdex.domain.viewmodels.ProfileViewModel
import com.application.portdex.domain.viewmodels.StoreViewModel
import com.application.portdex.presentation.base.BaseActivity
import com.application.portdex.presentation.login.LoginViewModel
import com.jacopo.pagury.prefs.PrefUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class VerifyActivity : BaseActivity() {

    companion object {
        const val INPUT_PROFILE = "VerifyActivity.INPUT_PROFILE"
        const val INPUT_STORE = "VerifyActivity.INPUT_STORE"
    }

    private val profile by lazy { intent?.getParcelableExtra<CreateProfileInfo>(INPUT_PROFILE) }
    private val store by lazy { intent?.getParcelableExtra<StoreInfo>(INPUT_STORE) }
    private val viewModel by viewModels<LoginViewModel>()
    private val storeViewModel by viewModels<StoreViewModel>()
    private val profileViewModel by viewModels<ProfileViewModel>()
    private lateinit var mBinding: VerifyActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = VerifyActivityBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        setSupportActionBar(mBinding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        initOtp()
    }

    private fun initOtp() {
        mBinding.infoNumber.text = getString(R.string.info_code_sent_to, profile?.phoneNo)
        mBinding.otpView.setAnimationEnable(true)
        mBinding.otpView.setOtpCompletionListener { otp ->
            mBinding.btnVerify.isEnabled = otp.isNotEmpty()
        }
        mBinding.btnVerify.setOnClickListener {
            PrefUtils.setLoginInfo(LoginInfo(number = profile?.phoneNo))
            mBinding.otpView.getValidString()?.let { otp ->
                showProgress()
                viewModel.confirmLogin(otp) { response ->
                    when (response) {
                        is Resource.Success -> fetchSession()
                        is Resource.Error -> {
                            hideProgress()
                            response.message?.let { showToast(it) }
                        }
                    }
                }
            }
        }
        mBinding.btnResend.setOnClickListener {
            profile?.phoneNo?.let {
                showProgress()
                viewModel.loginWithNumber(it) { resource ->
                    when (resource) {
                        is Resource.Success -> if (resource.data == true) showToast(R.string.info_code_sent_successfully)
                        is Resource.Error -> resource.message?.let { message -> showToast(message) }
                    }
                    hideProgress()
                }
            }
        }
    }

    private fun fetchSession() {
        viewModel.fetchAuthSession { response ->
            when (response) {
                is Resource.Success -> if (response.data == true) checkProfile()
                is Resource.Error -> response.message?.let {
                    hideProgress()
                    showToast(it)
                }
            }
        }
    }

    private fun checkProfile() {
        profile?.phoneNo?.let { number ->
            profileViewModel.getUserProfile(number) {
                hideProgress()
                when (it) {
                    is Resource.Success -> if (it.data.isProfileValid()) {
                        it.data?.let { profile -> PrefUtils.setProfileInfo(profile) }
                        startMainActivity()
                    } else createProfile()
                    is Resource.Error -> createProfile()
                }
            }
        }
    }

    private fun createProfile() {
        showProgress()
        createProfile { profile ->
            store?.let { store ->
                if (store.category == "Food" || store.category == "Retailer") {
                    store.userId = profile.userId
                    val imageFile = store.imageUri?.let { DocumentFileCompat.fromUri(this, it) }
                    storeViewModel.createStore(store, imageFile) { it.onStoreCreated(profile) }
                } else {
                    hideProgress()
                    startMainActivity(profile)
                }
            } ?: run {
                hideProgress()
                startMainActivity(profile)
            }
        }
    }

    private fun Resource<StoreInfo>.onStoreCreated(profile: ProfileInfo) {
        hideProgress()
        when (this) {
            is Resource.Success -> startMainActivity(profile)
            is Resource.Error -> message?.let {
                showToast(it)
                setResult(RESULT_CANCELED)
                onBackPressed()
            }
        }
    }

    private fun createProfile(listener: (ProfileInfo) -> Unit) {
        profile?.let { profile ->
            val imageFile = profile.imageUri?.let { DocumentFileCompat.fromUri(this, it) }
            profileViewModel.createProfile(profile, imageFile) {
                it.onProfileCreated(listener)
            }
        }
    }

    private fun Resource<ProfileInfo>.onProfileCreated(listener: (ProfileInfo) -> Unit) {
        when (this) {
            is Resource.Success -> data?.let { profile -> listener.invoke(profile) }
            is Resource.Error -> message?.let {
                hideProgress()
                showToast(it)
                onBackPressed()
            }
        }
    }

    private fun startMainActivity(profile: ProfileInfo) {
        PrefUtils.setLoggedIn(true)
        PrefUtils.setProfileInfo(profile)
        startMainActivity()
    }


    override fun onResume() {
        super.onResume()
        mBinding.otpView.requestFocus()
    }
}