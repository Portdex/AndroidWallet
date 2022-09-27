package com.application.portdex.core.utils

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.location.Address
import android.location.Geocoder
import android.provider.Settings
import android.util.Log
import android.view.View
import androidx.fragment.app.FragmentActivity
import androidx.palette.graphics.Palette
import com.application.portdex.App
import com.application.portdex.R
import com.application.portdex.domain.models.County
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.jacopo.pagury.prefs.PrefUtils
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import kotlin.random.Random

object GenericUtils {

    private const val TAG = "GenericUtils"

    fun View.inVisible(invisible: Boolean = true) {
        visibility = if (invisible) View.INVISIBLE else View.VISIBLE
    }

    fun View.show(show: Boolean = true) {
        visibility = if (show) View.VISIBLE else View.GONE
    }

    fun View.hide(hide: Boolean = true) {
        visibility = if (hide) View.GONE else View.VISIBLE
    }

    @SuppressLint("HardwareIds")
    fun getDeviceID(): String? {
        return Settings.Secure.getString(
            App.getContext().contentResolver,
            Settings.Secure.ANDROID_ID
        )
    }

    fun Palette?.getImagesBg(): GradientDrawable {
        val vibrantDark = this?.getDarkVibrantColor(Color.BLACK) ?: Color.BLACK
        val mutedDark = this?.getDarkMutedColor(Color.BLACK) ?: Color.BLACK
        return GradientDrawable(
            GradientDrawable.Orientation.TOP_BOTTOM,
            intArrayOf(vibrantDark, mutedDark)
        ).apply { shape = GradientDrawable.RECTANGLE }
    }

    fun getGradientBg(light: Boolean = false): GradientDrawable {
        return GradientDrawable(
            GradientDrawable.Orientation.TOP_BOTTOM,
            intArrayOf(getRandomColor(light), getRandomColor(light))
        )
    }

    fun getRandomColor(light: Boolean = false): Int {
        return if (light) Color.rgb(
            Random.nextInt(128) + 128,
            Random.nextInt(128) + 128,
            Random.nextInt(128) + 128
        )
        else Color.argb(
            255,
            Random.nextInt(50),
            Random.nextInt(50),
            Random.nextInt(50)
        )
    }

    fun FragmentActivity.getCountryList(): List<County> {
        return try {
            resources.openRawResource(R.raw.countries).bufferedReader()
                .use { it.readText() }
                .let { string ->
                    Gson().fromJson(string, object : TypeToken<List<County>>() {}.type)
                }
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    fun Context.getAddress(): Address? {
        val location = PrefUtils.getLocation() ?: return null
        val geocoder = Geocoder(this)
        return try {
            geocoder.getFromLocation(location.latitude, location.longitude, 1)?.firstOrNull()
        } catch (_: Exception) {
            null
        }
    }

    fun Context.getCountry(): String? {
        val address = getAddress() ?: return null
        Log.d(TAG, "getCountry: ${address.countryCode} : ${address.countryName}")
        return when (address.countryCode) {
            "uk", "uae",
            "UK", "UAE" -> address.countryCode.lowercase()
            else -> address.countryName.lowercase()
        }
    }

    fun String.requestBody(): RequestBody {
        return this.toRequestBody("application/json; charset=utf-8".toMediaType())
    }

}