package com.exquisite.a_mobile_kmm.core.screenUtils



import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.format.MonthNames
import kotlinx.datetime.format.char
import kotlinx.datetime.toLocalDateTime


fun String.getInitials(): String =
    trim()
        .split(Regex("\\s+"))
        .filter { it.isNotEmpty() }
        .joinToString("") { it.first().uppercaseChar().toString() }

fun String.formatToReadableDate(): String {
    return try {
        // Try parsing as ISO 8601 timestamp first (e.g., "2025-12-17T13:17:22.095Z")
        val date = try {
            val instant = Instant.parse(this)
            instant.toLocalDateTime(TimeZone.currentSystemDefault()).date
        } catch (e: Exception) {
            // If that fails, try parsing as date-only format (e.g., "2025-12-17")
            LocalDate.parse(this)
        }

        val format = LocalDate.Format {
            monthName(MonthNames.ENGLISH_ABBREVIATED)
            char(' ')
            dayOfMonth()
            chars(", ")
            year()
        }
        date.format(format)
    } catch (e: Exception) {
        this
    }
}

 fun String.containsAny(vararg keywords: String): Boolean {
    return keywords.any { keyword ->
        this.contains(keyword, ignoreCase = true)
    }
}




fun String.capitalizeWords(): String {
    return this.split(" ").joinToString(" ") {
        it.replaceFirstChar { char -> char.uppercase() }
    }
}



fun Double.formatBalance(): String {
    // Round to 2 decimal places
    val rounded = (this * 100).toLong() / 100.0

    // Split into integer and decimal parts
    val integerPart = rounded.toLong().toString()
    val decimalPart = ((rounded - integerPart.toLong()) * 100).toInt()

    // Add thousand separators to integer part
    val formattedInteger = integerPart.reversed().chunked(3).joinToString(",").reversed()

    // Format decimal part with leading zero if needed
    val formattedDecimal = decimalPart.toString().padStart(2, '0')

    return "$formattedInteger.$formattedDecimal"
}

fun Double.formatBalance(currencySymbol: String): String {
    // Round to 2 decimal places
    val rounded = (this * 100).toLong() / 100.0

    // Split into integer and decimal parts
    val integerPart = rounded.toLong().toString()
    val decimalPart = ((rounded - integerPart.toLong()) * 100).toInt()

    // Add thousand separators to integer part
    val formattedInteger = integerPart.reversed().chunked(3).joinToString(",").reversed()

    // Format decimal part with leading zero if needed
    val formattedDecimal = decimalPart.toString().padStart(2, '0')

    return "$currencySymbol$formattedInteger.$formattedDecimal"
}

fun String.formatTime():String{
    return this.split(" ").first()
}


fun String.toFullDateFormat(): String {
    return try {
        val date = LocalDate.parse(this)
        val format = LocalDate.Format {
            monthName(MonthNames.ENGLISH_FULL)
            char(' ')
            dayOfMonth()
            char(' ')
            year()
        }
        date.format(format)
    } catch (e: Exception) {
        this
    }
}

fun String.to12HourFormat(): String {
    return try {
        val parts = this.split(":")
        if (parts.size < 2) return this

        val hour = parts[0].toIntOrNull() ?: return this
        val minute = parts[1]

        val period = if (hour >= 12) "PM" else "AM"
        val displayHour = when {
            hour == 0 -> 12
            hour > 12 -> hour - 12
            else -> hour
        }

        "$displayHour:$minute $period"
    } catch (e: Exception) {
        this
    }
}

fun getTimeBasedGreeting(): String {
    val currentHour = Clock.System.now()
        .toLocalDateTime(TimeZone.currentSystemDefault())
        .hour

    return when (currentHour) {
        in 0..11 -> "Good Morning 😃"        // Morning 7:00 AM - 11:59 AM
        in 12..16 -> "Good Afternoon 😃"     // Afternoon 12:00 PM - 4:59 PM
        in 17..20 -> "Good Evening 😃"       // Evening 5:00 PM - 8:59 PM
        else -> "Hello there 👋🏼"              // Night 9:00 PM - 11:59 PM
    }
}

