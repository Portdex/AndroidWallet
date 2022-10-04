package com.application.portdex.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.application.portdex.data.local.chat.MessageEntity
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable

@Dao
interface MessageDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(message: MessageEntity): Completable

    @Query("SELECT * FROM messages WHERE sender =:sender AND receiver =:receiver")
    fun getMessages(sender: String?, receiver: String?): Flowable<List<MessageEntity>>
}