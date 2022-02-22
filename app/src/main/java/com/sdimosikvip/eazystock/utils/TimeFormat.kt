package com.sdimosikvip.eazystock.utils

import android.annotation.SuppressLint
import org.threeten.bp.Instant
import org.threeten.bp.ZonedDateTime
import org.threeten.bp.format.DateTimeFormatter

@SuppressLint("SimpleDateFormat")
fun formatDayMonthTime(timestamp: Long): String {
    val sdf = DateTimeFormatter.ofPattern("dd MMM kk:mm")
    val zdt = ZonedDateTime.ofInstant(Instant.ofEpochSecond(timestamp), ZonedDateTime.now().zone)
    return sdf.format(zdt)
}