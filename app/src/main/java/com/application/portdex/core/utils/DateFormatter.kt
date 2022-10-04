package com.application.portdex.core.utils

import java.text.SimpleDateFormat
import java.util.*

class DateFormatter private constructor() {

    init {
        throw AssertionError()
    }

    companion object {

        fun format(date: Date?, template: Template): String {
            return format(date, template.get())
        }

        fun format(date: Date?, format: String?): String {
            return if (date == null) "" else SimpleDateFormat(format, Locale.getDefault())
                .format(date)
        }

        fun isSameMinute(date1: Date?, date2: Date?): Boolean {
            require(!(date1 == null || date2 == null)) { "Dates must not be null" }
            val cal1 = Calendar.getInstance()
            cal1.time = date1
            val cal2 = Calendar.getInstance()
            cal2.time = date2
            val minute1 = cal1[Calendar.MINUTE]
            val minute2 = cal2[Calendar.MINUTE]
            return isSameDay(cal1, cal2) && minute1 == minute2
        }

        fun isSameDay(date1: Date?, date2: Date?): Boolean {
            require(!(date1 == null || date2 == null)) { "Dates must not be null" }
            val cal1 = Calendar.getInstance()
            cal1.time = date1
            val cal2 = Calendar.getInstance()
            cal2.time = date2
            return isSameDay(cal1, cal2)
        }

        fun isSameDay(cal1: Calendar?, cal2: Calendar?): Boolean {
            require(!(cal1 == null || cal2 == null)) { "Dates must not be null" }
            return cal1[Calendar.ERA] == cal2[Calendar.ERA] && cal1[Calendar.YEAR] == cal2[Calendar.YEAR] && cal1[Calendar.DAY_OF_YEAR] == cal2[Calendar.DAY_OF_YEAR]
        }

        fun isSameYear(date1: Date?, date2: Date?): Boolean {
            require(!(date1 == null || date2 == null)) { "Dates must not be null" }
            val cal1 = Calendar.getInstance()
            cal1.time = date1
            val cal2 = Calendar.getInstance()
            cal2.time = date2
            return isSameYear(cal1, cal2)
        }

        fun isSameYear(cal1: Calendar?, cal2: Calendar?): Boolean {
            require(!(cal1 == null || cal2 == null)) { "Dates must not be null" }
            return cal1[Calendar.ERA] == cal2[Calendar.ERA] &&
                    cal1[Calendar.YEAR] == cal2[Calendar.YEAR]
        }

        fun isToday(calendar: Calendar?): Boolean {
            return isSameDay(calendar, Calendar.getInstance())
        }

        fun isToday(date: Date?): Boolean {
            return isSameDay(date, Calendar.getInstance().time)
        }

        fun isYesterday(calendar: Calendar?): Boolean {
            val yesterday = Calendar.getInstance()
            yesterday.add(Calendar.DAY_OF_MONTH, -1)
            return isSameDay(calendar, yesterday)
        }

        fun isYesterday(date: Date?): Boolean {
            val yesterday = Calendar.getInstance()
            yesterday.add(Calendar.DAY_OF_MONTH, -1)
            return isSameDay(date, yesterday.time)
        }

        fun isCurrentYear(date: Date?): Boolean {
            return isSameYear(date, Calendar.getInstance().time)
        }

        fun isCurrentYear(calendar: Calendar?): Boolean {
            return isSameYear(calendar, Calendar.getInstance())
        }

        fun Date.isFutureTime(): Boolean {
            val selected = Calendar.getInstance().apply { time = this@isFutureTime }
            return selected.timeInMillis > System.currentTimeMillis()
        }
    }

    enum class Template(private val template: String) {
        STRING_DAY_MONTH_YEAR("d MMMM yyyy"),
        STRING_DAY_MONTH("d MMMM"),
        TIME("hh:mm a");

        fun get(): String {
            return template
        }
    }

    /**
     * Interface used to format dates before they were displayed (e.g. dialogs time, messages date headers etc.).
     */
    interface Formatter {
        /**
         * Formats an string representation of the date object.
         *
         * @param date The date that should be formatted.
         * @return Formatted text.
         */
        fun format(date: Date): String
    }
}