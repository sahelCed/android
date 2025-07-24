package com.example.spend.utils

import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

fun formatDate(isoDate: String): String {
    return try {
        val date = OffsetDateTime.parse(isoDate)
        val formatter = DateTimeFormatter.ofPattern("dd MMM yyyy - HH:mm", Locale.FRANCE)
        date.format(formatter)
    } catch (e: Exception) {
        isoDate // fallback si parsing échoue
    }
}