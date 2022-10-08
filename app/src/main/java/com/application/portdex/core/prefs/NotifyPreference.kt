package com.application.portdex.core.prefs

import android.content.Context
import android.content.SharedPreferences
import com.application.portdex.core.notification.NotificationInfo
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken

object NotifyPreference {

    private val TAG = NotifyPreference::class.java.simpleName
    private const val SHARED_PREFERENCES_NAME = "NotifyPreference.SHARED_PREFERENCES_NAME"
    private const val PREF_PARAM_IS_NOTIFICATIONS_ENABLED = "isNotificationsEnabled"

    private fun getSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
    }

    fun setIsNotificationsEnabled(context: Context, isNotificationsEnabled: Boolean) {
        getSharedPreferences(context).edit()
            .putBoolean(PREF_PARAM_IS_NOTIFICATIONS_ENABLED, isNotificationsEnabled).apply()
    }

    fun isNotificationsEnabled(context: Context): Boolean {
        return getSharedPreferences(context).getBoolean(PREF_PARAM_IS_NOTIFICATIONS_ENABLED, true)
    }

    fun setNotification(context: Context, notification: NotificationInfo) {
        val list = getNotifications(context, notification.key)
        list.add(notification)
        val gson = GsonBuilder().create()
        getSharedPreferences(context).edit().putString(notification.key, gson.toJson(list)).apply()
    }

    fun getNotifications(context: Context, key: String): MutableList<NotificationInfo> {
        val string = getSharedPreferences(context).getString(key, null)
        return if (string == null) mutableListOf() else {
            Gson().fromJson(string, object : TypeToken<MutableList<NotificationInfo>>() {}.type)
        }
    }

    fun clearNotification(context: Context, key: String) {
        getSharedPreferences(context).edit().remove(key).apply()
    }

    fun clearPreferences(context: Context) {
        val editor = getSharedPreferences(context).edit()
        editor.clear()
        editor.apply()
    }
}