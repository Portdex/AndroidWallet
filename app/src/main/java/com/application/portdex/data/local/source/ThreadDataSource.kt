package com.application.portdex.data.local.source

import android.util.Log
import com.application.portdex.core.utils.RxUtils.request
import com.application.portdex.data.local.chat.ThreadEntity
import com.application.portdex.data.local.dao.ThreadDao
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.kotlin.subscribeBy
import javax.inject.Inject

class ThreadDataSource @Inject constructor(
    private val threadDao: ThreadDao
) {
    companion object {
        private const val TAG = "ThreadDataSource"
    }

    fun getThreads(): Flowable<List<ThreadEntity>> {
        return threadDao.getThreads()
    }

    fun upsert(thread: ThreadEntity): Disposable {
        return Single.fromCallable {
            val item = threadDao.getThreadById(thread.threadId)
            if (item == null) {
                threadDao.insertAsync(thread)
            } else {
                item.body = thread.body
                item.updatedAt = System.currentTimeMillis()
                item.unreadCounts = item.unreadCounts + 1
                threadDao.updateAsync(item)
            }
        }.request().subscribeBy(onError = { Log.e(TAG, "insertItem: error", it) },
            onSuccess = { Log.d(TAG, "insertItem: success $it") })
    }

    fun insertItem(entity: ThreadEntity): Disposable {
        return threadDao.insert(entity).request()
            .subscribeBy(onError = { Log.e(TAG, "insertItem: error", it) },
                onSuccess = { Log.d(TAG, "insertItem: success $it") })
    }

    fun resetUnreadCounts(threadId: String?): Disposable {
        return threadDao.resetUnreadCounts(threadId).request()
            .subscribeBy(onError = { Log.e(TAG, "resetUnreadCounts: error", it) },
                onSuccess = { Log.d(TAG, "resetUnreadCounts: $it") })
    }

}