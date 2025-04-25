package com.gondroid.rayacashapp

import platform.Foundation.NSCalendar
import platform.Foundation.NSDate
import platform.Foundation.NSDateComponents
import platform.Foundation.NSDateFormatter
import platform.Foundation.NSTimeZone
import platform.Foundation.systemTimeZone

actual fun formatLocalDateTimeString(isoString: String): String {
    val dateTime = kotlinx.datetime.LocalDateTime.parse(isoString)

    val components = NSDateComponents().apply {
        year = dateTime.year.toLong()
        month = dateTime.monthNumber.toLong()
        day = dateTime.dayOfMonth.toLong()
        hour = dateTime.hour.toLong()
        minute = dateTime.minute.toLong()
        second = dateTime.second.toLong()
    }

    val calendar = NSCalendar.currentCalendar
    val date = calendar.dateFromComponents(components) ?: NSDate()

    val formatter = NSDateFormatter()
    formatter.dateFormat = "dd/MM/yyyy HH:mm:ss"
    formatter.timeZone = NSTimeZone.systemTimeZone

    return formatter.stringFromDate(date)
}
