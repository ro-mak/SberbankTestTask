package ru.makproductions.sberbanktesttask.model.room.db

import android.arch.persistence.room.TypeConverter
import java.util.*


class DateConverters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return if (value == null) null else Date(value)
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return (if (date == null) null else date.time)?.toLong()
    }
}