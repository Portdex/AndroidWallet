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
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.application.portdex.R


class NotificationUtils(private val mContext: Context) {

    private var notificationId = 0
    private var channelId = "push_notifications"
    private var channelName = "General"

    init {
        channelId = mContext.getString(R.string.default_notification_channel_id)
        channelName = mContext.getString(R.string.default_notification_channel_name)
    }

    fun sendNotification(title: String, body: String, bitmap: Bitmap? = null) {
        sendNotification(title, body, bitmap, null)
    }

    fun sendNotification(title: String, body: String, bitmap: Bitmap?, intent: Intent?) {
        var pendingIntent: PendingIntent? = null
        intent?.let {
            it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
            pendingIntent = PendingIntent.getActivity(
                mContext,
                notificationId++,
                intent,
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
            )
        }

        val notificationManager = mContext.getSystemService(Context.NOTIFICATION_SERVICE)
                as NotificationManager

        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        val notificationBuilder = NotificationCompat.Builder(mContext, channelId)
        notificationBuilder.setAutoCancel(true) //Automatically delete the notification
            .setSmallIcon(R.mipmap.ic_launcher) //Notification icon
            .setContentTitle(title)
            .setContentText(body)
            .setLargeIcon(bitmap)
            .setSound(defaultSoundUri)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .priority = NotificationCompat.PRIORITY_HIGH

        if (pendingIntent != null) {
            notificationBuilder.setContentIntent(pendingIntent)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Creating an Audio Attribute
            val audioAttributes = AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                .build()

            val importance = NotificationManager.IMPORTANCE_HIGH
            val notificationChannel = NotificationChannel(channelId, channelName, importance)
            notificationChannel.enableVibration(true)
            notificationChannel.enableLights(true)
            notificationChannel.setSound(defaultSoundUri, audioAttributes)
            notificationChannel.lightColor = ContextCompat.getColor(mContext, R.color.colorPrimary)
            notificationChannel.lockscreenVisibility = Notification.VISIBILITY_PUBLIC
            notificationManager.createNotificationChannel(notificationChannel)
        }
        notificationManager.notify(notificationId++, notificationBuilder.build())
    }

    fun clearNotifications(mContext: Context) {
        val notificationManager = mContext.getSystemService(Context.NOTIFICATION_SERVICE)
                as NotificationManager
        notificationManager.cancelAll()
    }
}