package com.application.portdex.core.utils

import android.content.res.Resources
import android.widget.ImageView
import com.application.portdex.R
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.jacopo.pagury.utils.GlideApp

object ImageUtils {


    fun ImageView.loadImage(url: String, placeHolder: Int = R.color.whiteDim) {
        GlideApp.with(this)
            .load(url)
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            .placeholder(placeHolder)
            .error(placeHolder)
            .into(this)
    }

    fun Int.toPx(): Int {
        return (this * Resources.getSystem().displayMetrics.density).toInt()
    }
}