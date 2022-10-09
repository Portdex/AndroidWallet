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
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.FragmentActivity
import androidx.palette.graphics.Palette
import com.application.portdex.App
import com.application.portdex.R
import com.application.portdex.domain.models.County
import com.application.portdex.domain.models.LocationInfo
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

    fun EditText.textSpaceWatcher() {
        doOnTextChanged { _, _, _, _ ->
            val text = text.toString()
            if (text.startsWith(" ")) setText(text.trim { it <= ' ' })
        }
    }

    fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        imm?.hideSoftInputFromWindow(windowToken, 0)
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

    fun Context.getUserLocation(): String? {
        val location = PrefUtils.getLocation() ?: return null
        return getUserLocation(location.latitude.toString(), location.longitude.toString())
    }

    fun Context.getUserLocation(latitude: String?, longitude: String?): String? {
        if (latitude.isNullOrEmpty() || longitude.isNullOrEmpty()) return null
        val location = LocationInfo(
            latitude = latitude.toDouble(),
            longitude = longitude.toDouble()
        )
        val address = getAddress(location)

        val city = address?.locality
        val state = address?.adminArea
        val country = address?.countryName
        val postalCode = address?.postalCode
        val knownName = address?.featureName

        Log.d(TAG, "getUserLocation: $city $state $country $postalCode $knownName")

        return "$state, $country"
    }

    private fun Context.getAddress(location: LocationInfo): Address? {
        val geocoder = Geocoder(this)
        return try {
            geocoder.getFromLocation(location.latitude, location.longitude, 1)?.firstOrNull()
        } catch (_: Exception) {
            null
        }
    }

    fun Context.getCountry(): String? {
        val location = PrefUtils.getLocation() ?: return null
        val address = getAddress(location) ?: return null
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