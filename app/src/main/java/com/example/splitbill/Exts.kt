package com.example.splitbill

import android.text.format.DateFormat
import java.text.DecimalFormat
import java.util.Calendar
import java.util.Locale


inline fun Long.toAmountFormat(): String {
    val formatter = DecimalFormat("###,###,###")
    val formattedAmt = formatter.format(this)
    return "¥ $formattedAmt"
}

inline fun Long.toTimeFormat1(): String {
    val calendar = Calendar.getInstance(Locale.getDefault())
    calendar.timeInMillis = this * 1000L
    return DateFormat.format("yyyy/MM/dd(E)",calendar).toString()
}

inline fun Long.toTimeFormat2(): String {
    val calendar = Calendar.getInstance(Locale.getDefault())
    calendar.timeInMillis = this * 1000L
    return DateFormat.format("yyyy/MM/dd(E) HH:MM",calendar).toString()
}
