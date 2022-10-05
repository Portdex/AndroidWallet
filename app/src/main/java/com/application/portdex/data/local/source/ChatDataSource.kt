package com.application.portdex.data.local.source

import android.util.Log
import com.application.portdex.core.utils.RxUtils.request
import com.application.portdex.data.local.chat.MessageEntity
import com.application.portdex.data.local.dao.MessageDao
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.kotlin.subscribeBy
import javax.inject.Inject

class ChatDataSource @Inject constructor(
    private val messageDao: MessageDao
) {

    companion object {
        private const val TAG = "ChatDataSource"
    }

    fun getChatList(chatUserId: String?): Flowable<List<MessageEntity>> {
        return messageDao.getMessages(chatUserId).request()
    }

    fun upsert(entity: MessageEntity): Disposable {
        return Single.fromCallable {
            val message = messageDao.getMessageById(entity.messageId)
            if (message == null) {
                messageDao.insertAsync(entity)
            }
        }.request().subscribeBy(onError = { Log.e(TAG, "insertItem: error", it) },
            onSuccess = { Log.d(TAG, "insertItem: success") })
    }

    fun insertItem(entity: MessageEntity): Disposable {
        return messageDao.insert(entity).request()
            .subscribeBy(onError = { Log.e(TAG, "insertItem: error", it) },
                onSuccess = { Log.d(TAG, "insertItem: success $it") })
    }

    fun clean(): Disposable {
        return messageDao.nukeTable().request()
            .subscribeBy(onError = { Log.e(TAG, "clean: ", it) },
                onComplete = { Log.d(TAG, "clean: success") })
    }
}