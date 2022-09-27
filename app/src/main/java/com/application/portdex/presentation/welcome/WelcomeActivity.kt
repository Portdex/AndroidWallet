package com.application.portdex.presentation.welcome

import android.content.Intent
import android.os.Bundle
import com.application.portdex.core.location.LocationPickerImpl
import com.application.portdex.core.utils.GenericUtils.hide
import com.application.portdex.databinding.WelcomeActivityBinding
import com.application.portdex.presentation.base.BaseActivity
import com.application.portdex.presentation.login.LoginActivity
import com.application.portdex.presentation.main.MainActivity

class WelcomeActivity : BaseActivity() {

    private val locationPicker = LocationPickerImpl()
    private lateinit var mBinding: WelcomeActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = WelcomeActivityBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        locationPicker.initLocation(this)
        locationPicker.onLocationReceived {
            mBinding.progressBar.hide()
        }
        mBinding.btnLogin.setOnClickListener {
            startWithAnim(Intent(this, LoginActivity::class.java))
        }
        mBinding.btnGuest.setOnClickListener {
            startWithAnim(Intent(this, MainActivity::class.java))
            finish()
        }
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