package com.application.portdex.core.utils

import android.widget.EditText
import com.jacopo.pagury.prefs.PrefUtils

object ValidationUtils {

    fun generatePassword(): String {
        return RandomUtil.generateKey(15)
    }

    fun isLoggedIn(): Boolean {
        return !PrefUtils.getLoginInfo()?.idToken.isNullOrEmpty()
    }

    fun EditText.getValidString(): String? {
        return if (text.isNullOrBlank()) null else text?.toString()?.trim()
    }

    fun Boolean?.isSuccess() = this ?: false
}