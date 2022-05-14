package com.ewind.newsapptest.util.ext

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import java.util.*

fun setDatePickerListener(
    calendar: Calendar,
    func: Calendar.() -> Unit
): DatePickerDialog.OnDateSetListener =
    DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.MONTH, month)
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
        calendar.apply(func)
    }

fun Context.showDatePicker(calendar: Calendar, func: Calendar.() -> Unit): DatePickerDialog {
    val datePickerListener = setDatePickerListener(calendar) {
        this.apply(func)
    }
    return DatePickerDialog(
        this, datePickerListener, calendar
            .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )
}

fun Context.showTimePicker(calendar: Calendar, time: (hour: Int, minute: Int) -> Unit) {
    val timePickerDialog = TimePickerDialog(
        this,
        TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
            time(hourOfDay, minute)
        }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true
    )
    timePickerDialog.setTitle("Select Time")
    timePickerDialog.show()
}