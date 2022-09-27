package com.application.portdex.presentation.welcome

import android.content.Intent
import android.os.Bundle
import com.application.portdex.databinding.WelcomeActivityBinding
import com.application.portdex.presentation.base.BaseActivity
import com.application.portdex.presentation.login.LoginActivity
import com.application.portdex.presentation.main.MainActivity

class WelcomeActivity : BaseActivity() {

    private lateinit var mBinding: WelcomeActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = WelcomeActivityBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        mBinding.btnLogin.setOnClickListener {
            startWithAnim(Intent(this, LoginActivity::class.java))
        }
        mBinding.btnGuest.setOnClickListener {
            startWithAnim(Intent(this, MainActivity::class.java))
            finish()
        }
    }

}