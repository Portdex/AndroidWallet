package com.application.portdex.core.utils

import android.content.res.Resources
import android.graphics.Bitmap
import android.util.Log
import android.util.TypedValue
import android.widget.ImageView
import com.application.portdex.R
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.jacopo.pagury.utils.GlideApp
import com.jacopo.pagury.utils.GlideRequests


object ImageUtils {

    private const val TAG = "ImageUtils"

    fun ImageView.loadImage(url: String, placeHolder: Int = R.color.whiteDim) {
        GlideApp.with(this)
            .load(url)
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            .placeholder(placeHolder)
            .error(placeHolder)
            .into(this)
    }

    fun loadBitmap(glideRequests: GlideRequests, url: String?, width: Int, height: Int): Bitmap? {
        return try {
            glideRequests.asBitmap()
                .load(url)
                .centerCrop()
                .transform(CircleCrop())
                .diskCacheStrategy(DiskCacheStrategy.DATA)
                .submit(width.toPx(), height.toPx())
                .get()
        } catch (e: Exception) {
            Log.e(TAG, "getBitmapfromUrl ", e)
            null
        }
    }

    fun Int.toPx(): Int {
        return (this * Resources.getSystem().displayMetrics.density).toInt()
    }

    fun Int.toDp(): Int {
        val displayMetrics = Resources.getSystem().displayMetrics
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            this.toFloat(),
            displayMetrics
        ).toInt()
    }
}