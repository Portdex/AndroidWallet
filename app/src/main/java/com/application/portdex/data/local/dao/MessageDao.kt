package com.application.portdex.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.application.portdex.data.local.chat.MessageEntity
import com.application.portdex.domain.models.chat.Threads
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single

@Dao
interface MessageDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(message: MessageEntity): Single<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAsync(message: MessageEntity): Long

    @Query("SELECT * FROM messages WHERE messageId =:messageId")
    fun getMessageById(messageId: String?): MessageEntity?

    @Query("SELECT * FROM messages WHERE chatUserId =:chatUserId")
    fun getMessages(chatUserId: String?): Flowable<List<MessageEntity>>

    @Query("DELETE FROM messages")
    fun nukeTable(): Completable
}