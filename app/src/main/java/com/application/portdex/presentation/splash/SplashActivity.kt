package com.application.portdex.presentation.splash

import android.annotation.SuppressLint
import android.os.Bundle
import com.application.portdex.R
import com.application.portdex.core.utils.ValidationUtils
import com.application.portdex.presentation.base.BaseActivity

@SuppressLint("CustomSplashScreen")
class SplashActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_activity)
        try {
            Thread.sleep(300)
        } catch (_: Exception) {
        }
        if (ValidationUtils.isLoggedIn()) startMainActivity() else startWelcomeActivity()
    }
}