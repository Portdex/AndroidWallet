package com.application.portdex.core.notification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.media.AudioAttributes
import android.media.RingtoneManager
import android.os.Build
import android.text.Html
import android.text.Spanned
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import com.application.portdex.R
import com.application.portdex.core.enums.NotifyChannel
import com.application.portdex.core.prefs.NotifyPreference

class NotificationUtil(private val mContext: Context) {

    private var notificationId = 0
    private var channel = NotifyChannel.General
    private var key: String? = null
    private var title: String? = null
    private var body: CharSequence? = null
    private var image: Bitmap? = null
    private var intent: Intent? = null
    private var backIntent: Intent? = null
    private var pendingIntent: PendingIntent? = null

    private val notificationManager = mContext.getSystemService(Context.NOTIFICATION_SERVICE)
            as NotificationManager?
    private val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)


    companion object {
        fun with(context: Context): NotificationUtil {
            return NotificationUtil(context)
        }
    }

    fun setChannel(channel: NotifyChannel): NotificationUtil {
        this.channel = channel
        return this
    }

    fun setKey(key: String?): NotificationUtil {
        this.key = key
        return this
    }

    fun setTitle(title: String?): NotificationUtil {
        this.title = title
        return this
    }

    fun setBody(body: CharSequence?): NotificationUtil {
        this.body = body
        return this
    }

    fun setImage(image: Bitmap?): NotificationUtil {
        this.image = image
        return this
    }

    fun setIntent(intent: Intent): NotificationUtil {
        this.intent = intent
        return this
    }

    fun setBackIntent(backIntent: Intent): NotificationUtil {
        this.backIntent = backIntent
        return this
    }

    fun build(): NotificationUtil {
        notificationId = if (key == null) notificationId++ else key?.hashCode() ?: 0
        intent?.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        val flag = PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        pendingIntent = backIntent?.let {
            it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
            val intents = arrayOf(backIntent, intent)
            PendingIntent.getActivities(mContext, notificationId, intents, flag)
        } ?: PendingIntent.getActivity(mContext, notificationId, intent, flag)
        return this
    }

    fun notifyChat() {
        if (body == null) {
            return
        }
        val builder = create()
        key?.let { builder.setStyle(getNotifications(it)) }
        notificationManager?.notify(notificationId, builder.build())
    }

    fun notifySimple() {
        if (body == null) {
            return
        }
        notificationManager?.notify(notificationId, create().build())
    }

    fun notifyGroup() {
        if (body == null) {
            return
        }
        val builder = create()
        builder.setGroup(key)
        key?.let { builder.setStyle(getNotifications(it)) }
        notificationManager?.notify(notificationId, builder.build())
    }

    private fun create(): NotificationCompat.Builder {
        val notificationBuilder = NotificationCompat.Builder(mContext, channel.id).apply {
            setAutoCancel(true) //Automatically delete the notification
            setSmallIcon(R.mipmap.ic_launcher) //Notification icon
            setContentIntent(pendingIntent)
            setContentTitle(title)
            setContentText(toHtml(body))
            setLargeIcon(image)
            setSound(defaultSoundUri)
            setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            priority = NotificationCompat.PRIORITY_HIGH
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Creating an Audio Attribute
            val audioAttributes = AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                .build()

            val importance = NotificationManager.IMPORTANCE_HIGH
            val notificationChannel = NotificationChannel(channel.id, channel.name, importance)
                .apply {
                    enableVibration(true)
                    enableLights(true)
                    setSound(defaultSoundUri, audioAttributes)
                    lightColor = ContextCompat.getColor(mContext, R.color.colorPrimary)
                    lockscreenVisibility = Notification.VISIBILITY_PUBLIC
                }
            notificationBuilder.setChannelId(channel.id)
            notificationManager?.createNotificationChannel(notificationChannel)
            notificationManager?.createNotificationChannel(notificationChannel)
        }
        return notificationBuilder
    }

    fun clearNotification(id: Int) {
        notificationManager?.cancel(id)
    }

    fun clearAll() {
        notificationManager?.cancelAll()
    }

    private fun getNotifications(key: String): NotificationCompat.InboxStyle {
        val list = NotifyPreference.getNotifications(mContext, key)
        val result = NotificationCompat.InboxStyle()
            .setBigContentTitle(list.first().title)
            .setSummaryText(getSummary(list.size))
        list.forEach { result.addLine(it.body) }
        return result
    }

    private fun getSummary(size: Int): String {
        return mContext.resources.getQuantityString(R.plurals.format_messages, size, size)
    }

    @Suppress("DEPRECATION")
    private fun toHtml(string: CharSequence?): Spanned {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) Html.fromHtml(
            string.toString(),
            HtmlCompat.FROM_HTML_MODE_LEGACY
        ) else Html.fromHtml(string.toString())
    }

    private fun getString(resource: Int): String {
        return mContext.getString(resource)
    }
}