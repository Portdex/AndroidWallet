package com.application.portdex.core.prefs

import android.content.Context
import android.content.SharedPreferences

object ActivityPreference {

    private const val SHARED_PREFERENCES_NAME = "ActivityPreference.SHARED_PREFERENCES_NAME"
    private fun getSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
    }

    fun isUserInChat(context: Context, key: String?): Boolean {
        return getSharedPreferences(context).getBoolean(key, false)
    }

    fun setUserInChat(context: Context, key: String?) {
        getSharedPreferences(context).edit().putBoolean(key, true).apply()
    }

    fun clearUserInChatKey(context: Context, key: String?) {
        getSharedPreferences(context).edit().remove(key).apply()
    }

    fun clearPreferences(context: Context) {
        val editor = getSharedPreferences(context).edit()
        editor.clear()
        editor.apply()
    }
}