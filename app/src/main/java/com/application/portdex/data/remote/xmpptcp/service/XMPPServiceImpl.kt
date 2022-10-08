package com.application.portdex.data.remote.xmpptcp.service

import android.app.Service
import android.content.Intent
import android.graphics.Bitmap
import android.os.Binder
import android.os.IBinder
import com.application.portdex.core.notification.NotificationUtils
import com.application.portdex.core.utils.ImageUtils
import com.application.portdex.domain.repository.ChatRepository
import com.jacopo.pagury.utils.GlideApp
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class XMPPServiceImpl @Inject constructor() : Service(), XMPPService {

    @Inject
    lateinit var chatRepository: ChatRepository

    private var mIBinder: IBinder? = null
    private var mIsClientBound = false
    private var notificationUtils: NotificationUtils? = null

    companion object {
        private const val TAG = "XMPPServiceImpl"
    }

    override fun onCreate() {
        super.onCreate()
        mIBinder = ServiceBinder()
        notificationUtils = NotificationUtils(applicationContext)
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
            notificationUtils?.sendNotification(
                user.firstName ?: "",
                message,
                getImage(user.profilePicUrl)
            )
        }
    }

    private fun getImage(url: String?): Bitmap? {
        return ImageUtils.loadBitmap(GlideApp.with(applicationContext), url, 40, 40)
    }

    override fun onUnbind(intent: Intent?): Boolean {
        mIsClientBound = false
        return true
    }

    override fun onRebind(intent: Intent?) {
        mIsClientBound = true
    }

    class ServiceBinder : Binder() {
        fun getService() = XMPPServiceImpl()
    }

}