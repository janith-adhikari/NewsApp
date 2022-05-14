package com.ewind.newsapptest.util.ext

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit


const val YYYY_MM_DD = "yyyy-MM-dd"
const val DD_MM_YYYY = "dd/MM/yyyy"
const val DD_MMM = "dd MMM"
const val DD = "dd"
const val HH_MM = "h:mm a"
const val DD_MMM_YYYY = "dd MMM, yyyy"
const val DD_MMM_YYYY_H_MM_A = "dd MMM, yyyy  h:mm a"
const val DD_MMMM_EEEE = "dd'%s' MMMM, EEEE"
const val DD_MMM_YYYY_H_MM_AP = "dd/MMM/yyyy h:mm a"
const val EEEE_DD_MMMM = "EEEE dd MMMM"
const val EEEE_DD_MMMM_YYYY = "EEEE, dd MMMM yyyy"
const val YYYY_MM_DD_hh_mm_ss = "yyyy-MM-dd HH:mm:ss"
const val YYYY_MM_DDThh_mm_ssZ = "yyyy-MM-dd'T'HH:mm:ss'Z'" //2019-11-28T16:10:00Z
const val EE_DD_MMM = "EE dd MMM"


fun String.toDate(format: String): Date? {
    val simpleDateFormat = SimpleDateFormat(format, Locale.US)
    return try {
        simpleDateFormat.parse(this)
    } catch (e: ParseException) {
        null
    }
}

fun Long.toDate(): Date {
    val date = Date()
    date.time = this
    return date
}

fun Date.toCustomDate(format: String): String {
    val simpleDateFormat = SimpleDateFormat(format, Locale.US)
    return simpleDateFormat.format(this)
}

fun String.toCustomDate(format: String): String {
    return this.toDate(YYYY_MM_DD)?.let {
        it.toCustomDate(format)
    } ?: ""
}

fun Calendar.calculateDays(calendarSec: Calendar): Long {
    val diff = this.timeInMillis - calendarSec.timeInMillis
    return TimeUnit.MILLISECONDS.toDays(diff)
}

fun String.toCustomDateWithPos(): String {
    val suffix = getDayOfMonthSuffix(this.toCustomDate(DD).toInt())
    val customDate = this.toCustomDate(DD_MMMM_EEEE)
    return String.format(customDate, suffix)
}

fun getDayOfMonthSuffix(n: Int): String {
    //checkArgument(n >= 1 && n <= 31, "illegal day of month: $n")
    if (n in 11..13) {
        return "th"
    }
    return when (n % 10) {
        1 -> "st"
        2 -> "nd"
        3 -> "rd"
        else -> "th"
    }
}

/**
 * different with current time milis
 */
fun Long.differentFromCurrent(): Long {
    val dif = System.currentTimeMillis().minus(this)
    return dif
}

/**
 * get date title
 */
fun Long.getDate(): String {
    val calendat = Calendar.getInstance()
    calendat.timeInMillis = this
    val today = Calendar.getInstance().apply {
        set(Calendar.HOUR_OF_DAY, 0)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
    }
    val yetdyCalendar = Calendar.getInstance().apply {
        set(Calendar.HOUR_OF_DAY, 0)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
        add(Calendar.DATE, -1)
    }

    return if (calendat.after(today)) {
        "Today"
    } else if (calendat.after(yetdyCalendar)) {
        "Yesterday"
    } else {
        val date = Date()
        date.time = this
        date.toCustomDate(DD_MMM_YYYY)
    }
}

/**
 * get time title
 */
fun Long.getTime(): String {
    val dif = this.differentFromCurrent()
    val min = TimeUnit.MILLISECONDS.toMinutes(dif)
    val hours = TimeUnit.MINUTES.toHours(min)
    val days = TimeUnit.HOURS.toDays(hours)
    return if (min.toInt() == 0) {
        "Just now"
    } else if (min < 60) {
        "$min min ago"
    } else {
        if (hours < 24) {
            "${hours.toInt()}h ago"
        } else {
            if (days < 2) {
                "Yesterday"
            } else {
                val date = Date()
                date.time = this
                date.toCustomDate(DD_MMM_YYYY)
            }
        }
    }
}

fun Long.milisToHours(): String {
    var secound = this.div(1000)
    var minite = 0
    var hours = 0

    if (secound >= 60) {
        minite = secound.div(60).toInt()
        secound %= 60
    }
    if (minite >= 60) {
        hours = minite.div(60).toInt()
        minite %= 60
    }

    return "${String.format("%02d", hours)}:${String.format("%02d", minite)}:${String.format(
        "%02d",
        secound
    )}"
}

fun Long.milisToMinute(): String {
    var secound = this.div(1000)
    var minite = 0

    if (secound >= 60) {
        minite = secound.div(60).toInt()
        secound %= 60
    }
    return "${String.format("%02d", minite)}:${String.format("%02d", secound)}"
}

fun Long.milisToSecound(): Long {
    val secound = this.div(1000)

    return secound
}