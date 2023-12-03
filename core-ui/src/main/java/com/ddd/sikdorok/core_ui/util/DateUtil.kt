package com.ddd.sikdorok.core_ui.util

import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import java.util.Calendar
import java.util.Date

object DateUtil {

    fun getMonthWithWeekCount(dateTime: DateTime = DateTime.now()): Int {
        val calendar = Calendar.getInstance().apply {
            set(dateTime.year, dateTime.monthOfYear, dateTime.dayOfMonth)
        }

        return calendar.get(Calendar.WEEK_OF_MONTH)
    }

    fun parseDate(dateString : String) : DateTime {
        return DateTime.parse(dateString, DateTimeFormat.forPattern("yyyy-MM-dd"))
    }
}