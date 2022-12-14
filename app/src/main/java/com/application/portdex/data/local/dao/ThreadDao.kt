package com.application.portdex.data.local.dao

import androidx.room.*
import com.application.portdex.data.local.chat.ThreadEntity
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single

@Dao
interface ThreadDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(thread: ThreadEntity): Single<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAsync(thread: ThreadEntity): Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateAsync(thread: ThreadEntity): Int

    @Query("UPDATE threads SET unreadCounts = 0 WHERE threadId =:threadId")
    fun resetUnreadCounts(threadId: String?): Single<Int>

    @Query("SELECT * FROM threads WHERE threadId =:threadId")
    fun getThreadById(threadId: String?): ThreadEntity?

    @Query("SELECT * FROM threads")
    fun getThreads(): Flowable<List<ThreadEntity>>

    @Query("SELECT SUM(unreadCounts) FROM threads")
    fun getUnreadCounts(): Flowable<Int>

    @Query("DELETE FROM threads")
    fun nukeTable(): Completable
}