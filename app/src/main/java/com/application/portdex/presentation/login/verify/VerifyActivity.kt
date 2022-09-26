package com.application.portdex.presentation.login.verify

import android.os.Bundle
import androidx.activity.viewModels
import com.application.portdex.R
import com.application.portdex.core.utils.ValidationUtils.getValidString
import com.application.portdex.data.utils.Resource
import com.application.portdex.databinding.VerifyActivityBinding
import com.application.portdex.domain.models.LoginInfo
import com.application.portdex.presentation.base.BaseActivity
import com.application.portdex.presentation.login.LoginViewModel
import com.jacopo.pagury.prefs.PrefUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class VerifyActivity : BaseActivity() {

    companion object {
        const val INPUT_NUMBER = "VerifyActivity.INPUT_NUMBER"
    }

    private val viewModel by viewModels<LoginViewModel>()
    private val number by lazy { intent?.getStringExtra(INPUT_NUMBER) }
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
                is Resource.Success -> if (response.data == true) startMainActivity()
                is Resource.Error -> response.message?.let { showToast(it) }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        mBinding.otpView.requestFocus()
    }
}