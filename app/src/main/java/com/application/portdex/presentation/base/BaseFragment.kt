package com.application.portdex.presentation.base

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment

open class BaseFragment : Fragment(), BaseView {

    val TAG: String = this.javaClass.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
    }

    override fun showProgress() {
        (activity as? BaseActivity)?.showProgress()
    }

    override fun showProgress(message: Int) {
        (activity as? BaseActivity)?.showProgress(message)
    }

    override fun showProgress(progress: Int, label: Int) {
        (activity as? BaseActivity)?.showProgress(progress, label)
    }

    override fun hideProgress() {
        (activity as? BaseActivity)?.hideProgress()
    }

    override fun showToast(messageId: Int) {
        (activity as? BaseActivity)?.showToast(messageId)
    }

    override fun showToast(message: String) {
        (activity as? BaseActivity)?.showToast(message)
    }

    override fun startMainActivity() {
        (activity as? BaseActivity)?.startMainActivity()
    }

    override fun startWelcomeActivity() {
        (activity as? BaseActivity)?.startWelcomeActivity()
    }

    override fun startWithAnim(intent: Intent) {
        (activity as? BaseActivity)?.startWithAnim(intent)
    }

    override fun startWithAnim(executor: Runnable) {
        (activity as? BaseActivity)?.startWithAnim(executor)
    }

    override fun startChatActivity(bundle: Bundle) {
        (activity as? BaseActivity)?.startChatActivity(bundle)
    }

    override fun authenticated(): Boolean {
        return (activity as? BaseActivity)?.authenticated() ?: false
    }
}