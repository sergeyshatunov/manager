package by.shatunov.manager.converter

import android.arch.persistence.room.TypeConverter
import java.util.*

class DateConverter {

    @TypeConverter
    fun fromDate(date: Date?) = date?.time

    @TypeConverter
    fun toDate(millisSinceEpoch: Long?) = millisSinceEpoch?.let { Date(it) }
}
