package com.application.portdex.presentation.base

import android.content.Intent
import android.os.Bundle
import androidx.annotation.StringRes

interface BaseView {

    fun showProgress()

    fun showProgress(message: Int)

    fun showProgress(progress: Int, label: Int)

    fun hideProgress()

    fun showToast(@StringRes messageId: Int)

    fun showToast(message: String)

    fun startMainActivity()

    fun startWelcomeActivity()

    fun startWithAnim(intent: Intent)

    fun startWithAnim(executor: Runnable)

    fun authenticated(): Boolean

    fun startChatActivity(bundle: Bundle)

    fun startCartActivity()
}