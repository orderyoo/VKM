package com.example.vkm.composeble

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.Date

@SuppressLint("SimpleDateFormat")
fun toDateFormatMMSS(time: Long): String {
    val dateFormat = SimpleDateFormat("mm:ss")
    return dateFormat.format(Date(time * 1000))
}

@SuppressLint("SimpleDateFormat")
fun toDateFormatDDMMYY(time: Long): String {
    val dateFormat = SimpleDateFormat("d.MM.yyyy")
    return dateFormat.format(Date(time * 1000))
}

