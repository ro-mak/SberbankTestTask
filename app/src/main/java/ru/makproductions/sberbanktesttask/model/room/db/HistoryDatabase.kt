package ru.makproductions.sberbanktesttask.model.room.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import android.content.Context
import ru.makproductions.sberbanktesttask.model.room.dao.HistoryDao
import ru.makproductions.sberbanktesttask.model.room.entity.RoomHistoryUnit

@Database(entities = [RoomHistoryUnit::class], version = 1)
@TypeConverters(DateConverters::class)
abstract class HistoryDatabase : RoomDatabase() {

    abstract val historyDao: HistoryDao

    companion object {
        private val DB_NAME = "historyDatabase.db"
        @Volatile
        private var instance: HistoryDatabase? = null

        @Synchronized
        fun getInstance(): HistoryDatabase? {
            if (instance == null) {
                throw NullPointerException("Database has not been created. Please call create()")
            }
            return instance
        }

        fun create(context: Context) {
            instance =
                Room.databaseBuilder(context, HistoryDatabase::class.java, DB_NAME).fallbackToDestructiveMigration()
                    .build()

        }
    }

}