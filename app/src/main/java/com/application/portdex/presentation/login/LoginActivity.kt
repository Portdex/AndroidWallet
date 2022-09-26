package com.application.portdex.presentation.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import com.application.portdex.R
import com.application.portdex.core.utils.GenericUtils.getCountryList
import com.application.portdex.core.utils.ValidationUtils.getValidString
import com.application.portdex.core.utils.ValidationUtils.isSuccess
import com.application.portdex.data.utils.Resource
import com.application.portdex.databinding.LoginActivityBinding
import com.application.portdex.domain.viewmodels.ProfileViewModel
import com.application.portdex.presentation.base.BaseActivity
import com.application.portdex.presentation.login.verify.VerifyActivity
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class LoginActivity : BaseActivity() {

    private val profileViewModel by viewModels<ProfileViewModel>()
    private val viewModel by viewModels<LoginViewModel>()
    private lateinit var mBinding: LoginActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = LoginActivityBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        mBinding.actionBack.setOnClickListener { onBackPressed() }
        initCountries()
        initListeners()
        viewModel.logOut { Log.d(TAG, "logOut: ${it.data}") }
    }

    private fun initListeners() {
        mBinding.btnLogin.setOnClickListener {
            inputValid()?.let { number ->
                showProgress()
                profileViewModel.getUserProfile(number) { resource ->
                    when (resource) {
                        is Resource.Success -> loginWithNumber(number)
                        is Resource.Error -> {
                            hideProgress()
                            resource.message?.let { showToast(it) }
                        }
                    }
                }
            }
        }
    }

    private fun loginWithNumber(number: String) {
        viewModel.login(number) { resource ->
            hideProgress()
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


}