package com.application.portdex.data.local.source

import android.util.Log
import com.application.portdex.core.utils.RxUtils.request
import com.application.portdex.data.local.chat.MessageEntity
import com.application.portdex.data.local.dao.MessageDao
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.kotlin.subscribeBy
import javax.inject.Inject

class ChatDataSource @Inject constructor(
    private val messageDao: MessageDao
) {

    companion object {
        private const val TAG = "ChatDataSource"
    }

    fun getChatList(sender: String?, receiver: String?): Flowable<List<MessageEntity>> {
        return messageDao.getMessages(sender, receiver).request()
    }

    fun insertItem(entity: MessageEntity): Disposable {
        return messageDao.insert(entity).request()
            .subscribeBy(onError = { Log.e(TAG, "insertItem: error", it) },
                onComplete = { Log.d(TAG, "insertItem: success") })
    }

}