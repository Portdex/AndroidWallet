package com.application.portdex.core.dataBinding

import android.content.Context
import android.util.TypedValue
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.viewpager2.widget.ViewPager2
import com.application.portdex.R
import com.application.portdex.adapters.FeedImagesAdapter
import com.application.portdex.core.utils.FormatUtils.formatTo
import com.application.portdex.core.utils.GenericUtils.getUserLocation
import com.application.portdex.core.utils.GenericUtils.show
import com.application.portdex.core.utils.ImageUtils.loadImage
import com.application.portdex.core.utils.ImageUtils.toDp
import com.application.portdex.core.utils.ImageUtils.toPx
import com.application.portdex.domain.models.FeedItem
import com.application.portdex.domain.models.ProviderInfo
import com.application.portdex.domain.models.category.SubCategory
import com.google.android.material.textview.MaterialTextView

object DataBinding {

    @JvmStatic
    @BindingAdapter("setImage")
    fun ImageView.setImage(url: String?) {
        url?.let { loadImage(it) }
    }

    @JvmStatic
    @BindingAdapter("setUserImage")
    fun ImageView.setUserImage(url: String?) {
        url?.let { loadImage(url = it, placeHolder = R.drawable.user_thumbnail) }
    }

    @JvmStatic
    @BindingAdapter("setFeedTime")
    fun MaterialTextView.setFeedTime(feed: FeedItem) {
        val time = feed.createdDateTime?.formatTo("dd MMM")
        text = if (time.isNullOrEmpty()) feed.country else "$time - ${feed.country}"
    }

    @JvmStatic
    @BindingAdapter("setFeedImages")
    fun ViewPager2.setFeedImages(list: List<SubCategory>) {
        show(list.isNotEmpty())
        layoutParams = ViewGroup.LayoutParams(0, if (list.isEmpty()) 0 else 200.toPx())
        adapter = FeedImagesAdapter().apply {
            addList(list.toMutableList())
        }
    }

    @JvmStatic
    @BindingAdapter("setProviderInfo")
    fun LinearLayout.setProviderInfo(provider: ProviderInfo) {
        val location = context.getUserLocation(provider.latitude, provider.longitude)
        location?.let { addView(context.getTextView(R.drawable.ic_location_small, it)) }
        provider.phoneNumber?.let { addView(context.getTextView(R.drawable.ic_phone_small, it)) }
    }

    private fun Context.getTextView(icon: Int, text: String): MaterialTextView {
        return MaterialTextView(this).apply {
            setTextColor(ContextCompat.getColor(context, R.color.textColorSecondaryLight))
            setTextSize(TypedValue.COMPLEX_UNIT_SP, 12f)
            setCompoundDrawablesWithIntrinsicBounds(icon, 0, 0, 0)
            compoundDrawablePadding = 8.toDp()
            setText(text)
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                setMargins(0, 4.toDp(), 0, 0)
            }
        }
    }
}