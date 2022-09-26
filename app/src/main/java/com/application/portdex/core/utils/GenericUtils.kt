package com.application.portdex.core.utils

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.View
import androidx.fragment.app.FragmentActivity
import androidx.palette.graphics.Palette
import com.application.portdex.R
import com.application.portdex.domain.models.County
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import kotlin.random.Random

object GenericUtils {

    fun View.inVisible(invisible: Boolean = true) {
        visibility = if (invisible) View.INVISIBLE else View.VISIBLE
    }

    fun View.show(show: Boolean = true) {
        visibility = if (show) View.VISIBLE else View.GONE
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

    fun String.requestBody(): RequestBody {
        return this.toRequestBody("application/json; charset=utf-8".toMediaType())
    }

}