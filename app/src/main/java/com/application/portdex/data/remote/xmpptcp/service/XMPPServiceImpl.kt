package com.application.portdex.data.remote.xmpptcp.service

import android.app.Service
import android.content.Intent
import android.graphics.Bitmap
import android.os.Binder
import android.os.IBinder
import android.util.Log
import com.application.portdex.core.enums.NotifyChannel
import com.application.portdex.core.notification.NotificationInfo
import com.application.portdex.core.notification.NotificationUtil
import com.application.portdex.core.prefs.ActivityPreference
import com.application.portdex.core.prefs.NotifyPreference
import com.application.portdex.core.utils.ImageUtils
import com.application.portdex.domain.repository.ChatRepository
import com.application.portdex.presentation.chat.activity.ChatActivity
import com.application.portdex.presentation.main.MainActivity
import com.jacopo.pagury.utils.GlideApp
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class XMPPServiceImpl @Inject constructor() : Service(), XMPPService {

    @Inject
    lateinit var chatRepository: ChatRepository

    private var mIBinder: IBinder? = null
    private var mIsClientBound = false
    private var notificationUtils: NotificationUtil? = null

    companion object {
        private const val TAG = "XMPPServiceImpl"
    }

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "onCreate: ")
        mIBinder = ServiceBinder()
        notificationUtils = NotificationUtil.with(applicationContext)
    }

    override fun onBind(intent: Intent?): IBinder? {
        mIsClientBound = true
        return mIBinder
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        initConnection()
        return START_STICKY
    }

    private fun initConnection() {
        chatRepository.newMessageListener { user, message ->

            user.userId?.let {
                val info = NotificationInfo(it, user.firstName, message)
                NotifyPreference.setNotification(applicationContext, info)
            }

            val backIntent = Intent(this, MainActivity::class.java)
            val intent = Intent(this, ChatActivity::class.java)
            intent.putExtra(ChatActivity.PROFILE_ITEM, user)

            if (!ActivityPreference.isUserInChat(this, user.userId)) {
                notificationUtils
                    ?.setChannel(NotifyChannel.Messages)
                    ?.setKey(user.userId)
                    ?.setTitle(user.firstName)
                    ?.setBody(message)
                    ?.setImage(getImage(user.profilePicUrl))
                    ?.setIntent(intent)
                    ?.setBackIntent(backIntent)
                    ?.build()
                    ?.notifyChat()
            }
        }
    }

    private fun getImage(url: String?): Bitmap? {
        return ImageUtils.loadBitmap(GlideApp.with(applicationContext), url, 40, 40)
    }

    override fun onUnbind(intent: Intent?): Boolean {
        Log.d(TAG, "onUnbind: ")
        mIsClientBound = false
        return true
    }

    override fun onRebind(intent: Intent?) {
        Log.d(TAG, "onRebind: ")
        mIsClientBound = true
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy: ")
        ActivityPreference.clearPreferences(applicationContext)
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        super.onTaskRemoved(rootIntent)
        Log.d(TAG, "onTaskRemoved: ")
        ActivityPreference.clearPreferences(applicationContext)
    }

    class ServiceBinder : Binder() {
        fun getService() = XMPPServiceImpl()
    }

}