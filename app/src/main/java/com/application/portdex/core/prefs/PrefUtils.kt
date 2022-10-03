package com.jacopo.pagury.prefs

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.application.portdex.BuildConfig
import com.application.portdex.domain.models.LocationInfo
import com.application.portdex.domain.models.LoginInfo
import com.application.portdex.domain.models.ProfileInfo
import com.google.gson.Gson


object PrefUtils {

    private const val PREF_NAME = "PrefUtils.${BuildConfig.APPLICATION_ID}"
    private const val PREF_LOCATION = "PrefUtils.PREF_LOCATION"
    private const val PREF_LOGIN_INFO = "PrefUtils.PREF_LOGIN_INFO"
    private const val PREF_PROFILE_INFO = "PrefUtils.PREF_PROFILE_INFO"
    private const val PREF_USERNAME = "PrefUtils.PREF_AMPLIFY_USERNAME"


    private var preferences: SharedPreferences? = null

    fun initialize(context: Context) {
        val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
        preferences = EncryptedSharedPreferences.create(
            PREF_NAME, masterKeyAlias, context,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    fun saveLocation(location: LocationInfo) {
        preferences?.edit()?.putString(PREF_LOCATION, Gson().toJson(location))?.apply()
    }

    fun getLocation(): LocationInfo? {
        return preferences?.getString(PREF_LOCATION, null)?.let {
            Gson().fromJson(it, LocationInfo::class.java)
        }
    }

    fun setLoginInfo(info: LoginInfo) {
        preferences?.edit()?.putString(PREF_LOGIN_INFO, Gson().toJson(info))?.apply()
    }

    fun getLoginInfo(): LoginInfo? {
        return preferences?.getString(PREF_LOGIN_INFO, null)?.let { string ->
            Gson().fromJson(string, LoginInfo::class.java)
        }
    }

    fun setProfileInfo(profile: ProfileInfo) {
        preferences?.edit()?.putString(PREF_PROFILE_INFO, Gson().toJson(profile))?.apply()
    }

    fun getProfileInfo(): ProfileInfo? {
        return preferences?.getString(PREF_PROFILE_INFO, null)?.let { string ->
            Gson().fromJson(string, ProfileInfo::class.java)
        }
    }

    fun setUserName(userName: String) {
        preferences?.edit()?.putString(PREF_USERNAME, userName)?.apply()
    }

    fun getUserName(): String? {
        return preferences?.getString(PREF_USERNAME, null)
    }

    fun getChatId(): String? {
        return getProfileInfo()?.userId?.plus("@chat.portdex.com")
    }

    fun clear() {
        val location = getLocation()
        preferences?.edit()?.clear()?.apply()?.run {
            location?.let { saveLocation(it) }
        }
    }
}