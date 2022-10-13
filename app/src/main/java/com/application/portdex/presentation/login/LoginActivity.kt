package com.application.portdex.presentation.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import com.application.portdex.R
import com.application.portdex.core.location.LocationPickerImpl
import com.application.portdex.core.utils.GenericUtils.getCountryList
import com.application.portdex.core.utils.ValidationUtils.getValidString
import com.application.portdex.core.utils.ValidationUtils.isProfileValid
import com.application.portdex.data.utils.Resource
import com.application.portdex.databinding.LoginActivityBinding
import com.application.portdex.domain.viewmodels.ProfileViewModel
import com.application.portdex.presentation.base.BaseActivity
import com.application.portdex.presentation.dialogs.ProfileSelectionSheet
import com.application.portdex.presentation.login.business.LoginBusinessActivity
import com.jacopo.pagury.prefs.PrefUtils
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class LoginActivity : BaseActivity() {

    private val profileViewModel by viewModels<ProfileViewModel>()
    private val viewModel by viewModels<LoginViewModel>()
    private lateinit var mBinding: LoginActivityBinding
    private val locationPicker = LocationPickerImpl()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = LoginActivityBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        mBinding.actionBack.setOnClickListener { onBackPressed() }
        initCountries()
        initListeners()
    }

    override fun onResume() {
        super.onResume()
        viewModel.logOut {
            Log.d(TAG, "logOut: ${it.data}")
            locationPicker.initLocation(this)
        }
    }

    private fun initListeners() {
        mBinding.btnLogin.setOnClickListener {
            inputValid()?.let { number ->
                showProgress()
                profileViewModel.getUserProfile(number) {
                    when (it) {
                        is Resource.Success -> if (it.data.isProfileValid()) {
                            it.data?.let { profile -> PrefUtils.setProfileInfo(profile) }
                            startMainActivity()
                        } else openProfileSetup(number)
                        is Resource.Error -> {
                            hideProgress()
                            it.message?.let { showToast(it) }
                        }
                    }
                }
            }
        }
    }

    private fun openProfileSetup(number: String) {
        hideProgress()
        val dialog = ProfileSelectionSheet.newInstance()
        dialog.onProfileSelection = { isBusiness -> onProfileSelection(isBusiness, number) }
        dialog.show(supportFragmentManager, dialog.tag)
    }

    private fun onProfileSelection(isBusiness: Boolean, number: String) {
        startWithAnim(Intent(this, LoginBusinessActivity::class.java).apply {
            putExtra(LoginBusinessActivity.INPUT_NUMBER, number)
            putExtra(LoginBusinessActivity.IS_BUSINESS, isBusiness)
        })
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
        locationPicker.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

}