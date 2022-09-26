package com.application.portdex.presentation.base

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.application.portdex.R
import com.application.portdex.core.utils.ValidationUtils
import com.application.portdex.presentation.login.LoginActivity
import com.application.portdex.presentation.main.MainActivity
import com.application.portdex.presentation.welcome.WelcomeActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.kaopiz.kprogresshud.KProgressHUD

open class BaseActivity : AppCompatActivity(), BaseView {

    val TAG: String = this.javaClass.simpleName

    private var progressDialog: KProgressHUD? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
    }

    override fun showProgress() {
        showProgress(R.string.label_loading)
    }

    override fun showProgress(message: Int) {
        hideProgress()
        runOnUiThread {
            progressDialog = KProgressHUD.create(this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show()
        }
    }

    override fun showProgress(progress: Int, label: Int) {
        runOnUiThread {
            if (progressDialog == null) {
                progressDialog = KProgressHUD.create(this)
                    .setStyle(KProgressHUD.Style.PIE_DETERMINATE)
                    .setLabel(getString(label))
                    .setCancellable(false)
                    .setMaxProgress(100)
                    .show()
            }
            progressDialog?.setProgress(progress)
        }
    }

    override fun hideProgress() {
        runOnUiThread {
            if (progressDialog != null) {
                progressDialog?.dismiss()
                progressDialog = null
            }
        }
    }

    override fun showToast(messageId: Int) {
        runOnUiThread {
            Toast.makeText(this, messageId, Toast.LENGTH_SHORT).show()
        }
    }

    override fun showToast(message: String) {
        runOnUiThread {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }
    }

    override fun startMainActivity() {
        startWithAnim(Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        })
        finish()
    }

    override fun startWelcomeActivity() {
        startWithAnim(Intent(this, WelcomeActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        })
        finish()
    }

    override fun startWithAnim(intent: Intent) {
        startActivity(intent)
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    }

    override fun startWithAnim(executor: Runnable) {
        executor.run()
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    }

    private fun startLoginActivity() {
        startWithAnim(Intent(this, LoginActivity::class.java))
    }

    override fun authenticated(): Boolean {
        return ValidationUtils.isLoggedIn().also {
            if (!it) MaterialAlertDialogBuilder(this)
                .setIcon(R.drawable.ic_info_large)
                .setTitle(R.string.label_attention)
                .setMessage(R.string.info_authentication_required)
                .setNegativeButton(R.string.button_cancel, null)
                .setPositiveButton(R.string.button_proceed) { _, _ -> startLoginActivity() }
                .show()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}