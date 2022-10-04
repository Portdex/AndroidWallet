package com.application.portdex.core.utils

import java.text.SimpleDateFormat
import java.util.*

object FormatUtils {

    fun String?.formatTo(): Date? { // 2022-06-07T11:49:34.621Z
        return try {
            this?.let { dateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX").parse(this) }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun Date.formatTo(pattern: String): String {
        return try {
            dateFormat(pattern).format(this)
        } catch (e: Exception) {
            e.printStackTrace()
            "-"
        }
    }

    fun Long.formatTo(): Date {
        return Date(this)
    }


    fun Long.formatTo(pattern: String): String {
        return try {
            dateFormat(pattern).format(this)
        } catch (e: Exception) {
            e.printStackTrace()
            "-"
        }
    }

    private fun dateFormat(pattern: String): SimpleDateFormat {
        return SimpleDateFormat(pattern, Locale.getDefault())
    }

}