package com.application.portdex.core.utils

import android.widget.EditText
import com.application.portdex.domain.models.ProfileInfo
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

    fun ProfileInfo?.isProfileValid(): Boolean {
        return (!this?.firstName.isNullOrEmpty() || !this?.lastName.isNullOrEmpty())
                && !this?.phoneNumber.isNullOrEmpty()
    }

    fun Boolean?.isSuccess() = this ?: false
}