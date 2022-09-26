package com.application.portdex.presentation.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import com.anggrayudi.storage.file.DocumentFileCompat
import com.application.portdex.R
import com.application.portdex.core.filePicker.FilePicker
import com.application.portdex.core.filePicker.FilePickerImpl
import com.application.portdex.core.utils.GenericUtils.getCountryList
import com.application.portdex.core.utils.ValidationUtils.getValidString
import com.application.portdex.core.utils.ValidationUtils.isSuccess
import com.application.portdex.data.utils.Resource
import com.application.portdex.databinding.LoginActivityBinding
import com.application.portdex.domain.models.CreateProfileInfo
import com.application.portdex.domain.viewmodels.ProfileViewModel
import com.application.portdex.domain.viewmodels.RegistrationViewModel
import com.application.portdex.presentation.base.BaseActivity
import com.application.portdex.presentation.dialogs.ProfileSetupSheet
import com.application.portdex.presentation.login.business.LoginBusinessActivity
import com.application.portdex.presentation.login.verify.VerifyActivity
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class LoginActivity : BaseActivity(), FilePicker by FilePickerImpl() {

    private val viewModel by viewModels<LoginViewModel>()
    private val profileViewModel by viewModels<ProfileViewModel>()
    private val registrationViewModel by viewModels<RegistrationViewModel>()
    private lateinit var mBinding: LoginActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = LoginActivityBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        mBinding.actionBack.setOnClickListener { onBackPressed() }
        initPicker(this)
        initCountries()
        initListeners()
        viewModel.logOut { Log.d(TAG, "logOut: ${it.data}") }
    }

    private fun initListeners() {
        mBinding.btnLogin.setOnClickListener {
            inputValid()?.let { number ->
                showProgress()
                profileViewModel.getUserProfile(number) {
                    when (it) {
                        is Resource.Success -> performLogin(number)
                        is Resource.Error -> openProfileSetup(number)
                    }
                }
            }
        }
    }

    private fun performLogin(number: String) {
        viewModel.loginWithNumber(number) { resource ->
            when (resource) {
                is Resource.Success -> if (resource.data.isSuccess()) startVerification(number)
                is Resource.Error -> resource.message?.let { showToast(it) }
            }
        }
    }

    private fun startVerification(number: String) {
        startWithAnim(Intent(this, VerifyActivity::class.java).apply {
            putExtra(VerifyActivity.INPUT_NUMBER, number)
        })
    }

    private fun openProfileSetup(number: String) {
        hideProgress()
        val dialog = ProfileSetupSheet.newInstance()
        dialog.pickImageListener = { pickImage() }
        dialog.onContinueListener = { imageUri, userName, isBusiness ->
            if (isBusiness) startWithAnim(Intent(this, LoginBusinessActivity::class.java))
            else {
                val profile = CreateProfileInfo(
                    phoneNo = number,
                    firstName = userName,
                    imageFile = imageUri?.let { DocumentFileCompat.fromUri(this, it) }
                )
                createProfile(profile)
            }
        }
        dialog.show(supportFragmentManager, dialog.tag)
        setPickImageListener { uri ->
            if (dialog.isVisible) dialog.setImage(uri)
        }
    }

    private fun createProfile(profileInfo: CreateProfileInfo) {
        registrationViewModel.createProfile(profileInfo)
    }

    private fun initCountries() {
        getCountryList().let { countries ->
            val list = countries.map { it.dialCode }
            val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, list)
            mBinding.inputCountry.setAdapter(adapter)
            mBinding.inputCountry.setOnClickListener {
                mBinding.inputCountry.showDropDown()
            }
            countries.find { it.isoCode == Locale.getDefault().country }.let { country ->
                mBinding.inputCountry.setText(country?.dialCode, false)
                mBinding.inputCountry.setSelection(mBinding.inputCountry.text.length)
            }
        }
    }

    private fun inputValid(): String? {
        mBinding.inputNumber.error = null

        val code = mBinding.inputCountry.getValidString()
        val number = mBinding.inputNumber.getValidString()
        return if (code.isNullOrEmpty() || number.isNullOrEmpty()) {
            mBinding.inputNumber.error = getString(R.string.error_input_required)
            mBinding.inputNumber.requestFocus()
            null
        } else code + number
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

}