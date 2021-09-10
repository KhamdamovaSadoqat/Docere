package uz.medion.utils

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object DateTimeUtils {

    /**
     * Formats date which is Date format to String format
     *
     * @param date date which needs to be converted to String
     * @param format format which needs to be used
     *
     * @return String?
     */
    fun dateToText(date: Date?, format: String): String? {
        return if (date != null) {
            val sdf = SimpleDateFormat(format)
            sdf.format(date)
        } else
            null

    }

    /**
     * Formats the String formatted date to Date format
     *
     * @param dateText date in the String format
     * @param format format which needs to be used
     *
     * @return Date?
     */
    fun textToDate(dateText: String?, format: String): Date? {
        return if (dateText != null) {
            val sdf = SimpleDateFormat(format)
            try {
                sdf.parse(dateText)
            } catch (e: ParseException) {
                null
            }
        } else
            null
    }
}