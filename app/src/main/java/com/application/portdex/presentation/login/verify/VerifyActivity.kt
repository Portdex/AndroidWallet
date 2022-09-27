package com.application.portdex.presentation.login.verify

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.anggrayudi.storage.file.DocumentFileCompat
import com.application.portdex.R
import com.application.portdex.core.filePicker.FilePickerImpl
import com.application.portdex.core.utils.GenericUtils.getCountry
import com.application.portdex.core.utils.GenericUtils.getDeviceID
import com.application.portdex.core.utils.ValidationUtils.getValidString
import com.application.portdex.core.utils.ValidationUtils.isProfileValid
import com.application.portdex.data.mappers.toProfileInfo
import com.application.portdex.data.utils.Resource
import com.application.portdex.databinding.VerifyActivityBinding
import com.application.portdex.domain.models.CreateProfileInfo
import com.application.portdex.domain.models.LoginInfo
import com.application.portdex.domain.viewmodels.ProfileViewModel
import com.application.portdex.presentation.base.BaseActivity
import com.application.portdex.presentation.dialogs.ProfileSetupSheet
import com.application.portdex.presentation.login.LoginViewModel
import com.application.portdex.presentation.login.business.LoginBusinessActivity
import com.jacopo.pagury.prefs.PrefUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class VerifyActivity : BaseActivity() {

    companion object {
        const val INPUT_NUMBER = "VerifyActivity.INPUT_NUMBER"
    }

    private val filePicker = FilePickerImpl()
    private val viewModel by viewModels<LoginViewModel>()
    private val profileViewModel by viewModels<ProfileViewModel>()
    private val number by lazy { intent?.getStringExtra(INPUT_NUMBER) }
    private lateinit var mBinding: VerifyActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = VerifyActivityBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        setSupportActionBar(mBinding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        filePicker.initPicker(this)
        initOtp()
    }

    private fun initOtp() {
        mBinding.infoNumber.text = getString(R.string.info_code_sent_to, number)
        mBinding.otpView.setAnimationEnable(true)
        mBinding.otpView.setOtpCompletionListener { otp ->
            mBinding.btnVerify.isEnabled = otp.isNotEmpty()
        }
        mBinding.btnVerify.setOnClickListener {
            PrefUtils.setLoginInfo(LoginInfo(number = number))
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
            number?.let {
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
            hideProgress()
            when (response) {
                is Resource.Success -> if (response.data == true) checkProfile()
                is Resource.Error -> response.message?.let { showToast(it) }
            }
        }
    }

    private fun checkProfile() {
        number?.let { number ->
            profileViewModel.getUserProfile(number) {
                when (it) {
                    is Resource.Success -> if (it.data.isProfileValid()) {
                        it.data?.let { profile -> PrefUtils.setProfileInfo(profile) }
                        startMainActivity()
                    } else openProfileSetup(number)
                    is Resource.Error -> openProfileSetup(number)
                }
            }
        }
    }

    private fun openProfileSetup(number: String) {
        hideProgress()
        val dialog = ProfileSetupSheet.newInstance()
        dialog.pickImageListener = { filePicker.pickImage() }
        dialog.onContinueListener = { imageUri, userName, isBusiness ->
            if (isBusiness) startWithAnim(Intent(this, LoginBusinessActivity::class.java))
            else {
                showProgress()
                val location = PrefUtils.getLocation()
                val imageFile = imageUri?.let { DocumentFileCompat.fromUri(this, it) }
                val profile = CreateProfileInfo(
                    phoneNo = number,
                    firstName = userName,
                    latitude = location?.latitude?.toString(),
                    longitude = location?.longitude?.toString(),
                    country = getCountry(),
                    category = "User",
                    userToken = getDeviceID()
                )
                profileViewModel.createProfile(profile, imageFile) { it.onProfileCreated(profile) }
            }
        }
        dialog.show(supportFragmentManager, dialog.tag)
        filePicker.setPickImageListener { uri ->
            if (dialog.isVisible) dialog.setImage(uri)
        }
    }

    private fun Resource<Boolean>.onProfileCreated(profile: CreateProfileInfo) {
        hideProgress()
        when (this) {
            is Resource.Success -> if (data == true) {
                PrefUtils.setProfileInfo(profile.toProfileInfo())
                startMainActivity()
            }
            is Resource.Error -> message?.let {
                showToast(it)
                onBackPressed()
            }
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


    override fun onResume() {
        super.onResume()
        mBinding.otpView.requestFocus()
    }
}