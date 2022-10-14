package com.application.portdex.presentation.splash

import android.annotation.SuppressLint
import android.os.Bundle
import com.application.portdex.R
import com.application.portdex.core.utils.ValidationUtils
import com.application.portdex.presentation.base.BaseActivity
import com.jacopo.pagury.prefs.PrefUtils
import dagger.hilt.android.AndroidEntryPoint

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_activity)
        if (PrefUtils.isLoggedIn()) startMainActivity() else startWelcomeActivity()
    }
}