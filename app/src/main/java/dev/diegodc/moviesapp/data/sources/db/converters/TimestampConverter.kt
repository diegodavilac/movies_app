package dev.diegodc.moviesapp.data.sources.db.converters

import androidx.room.TypeConverter
import java.text.SimpleDateFormat
import java.util.*

class TimestampConverter {
    companion object {
        const val FORMAT = "yyyy-MM-dd"
    }
    @TypeConverter
    fun fromTimestamp(value: Long?): String? {
        val format = SimpleDateFormat(FORMAT, Locale.getDefault())
        val date = Date()
        date.time = value ?: 0
        return if (value == null) null else format.format(date)
    }

    @TypeConverter
    fun dateToTimestamp(dateStr: String?): Long? {
        val format = SimpleDateFormat(FORMAT, Locale.getDefault())
        val date = format.parse(dateStr?:"")
        return date?.time
    }
}