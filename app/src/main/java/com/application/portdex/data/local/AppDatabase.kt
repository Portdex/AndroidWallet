package com.application.portdex.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.application.portdex.data.local.chat.MessageEntity
import com.application.portdex.data.local.converters.MessageTypeConverter
import com.application.portdex.data.local.converters.StatusConverter
import com.application.portdex.data.local.dao.MessageDao

@TypeConverters(StatusConverter::class, MessageTypeConverter::class)
@Database(entities = [MessageEntity::class], exportSchema = false, version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract val message: MessageDao

    companion object {
        private const val DB_NAME: String = "aladino"

        @Volatile
        private var instance: AppDatabase? = null
        fun getDatabase(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                val database = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    DB_NAME
                ).build()
                instance = database
                database
            }
        }
    }
}