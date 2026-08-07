package com.exquisite.a_mobile_kmm.core.screenUtils



import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.format.MonthNames
import kotlinx.datetime.format.Padding
import kotlinx.datetime.format.char
import kotlinx.datetime.toLocalDateTime


fun String.getInitials(): String =
    trim()
        .split(Regex("\\s+"))
        .filter { it.isNotEmpty() }
        .joinToString("") { it.first().uppercaseChar().toString() }

fun String.formatToReadableDate(): String {
    return try {
        val date = when {
            // Handle ISO 8601 with timezone (e.g., "2025-12-17T13:17:22.095Z")
            this.contains("Z") || this.contains("+") -> {
                val instant = Instant.parse(this)
                instant.toLocalDateTime(TimeZone.currentSystemDefault()).date
            }
            // Handle datetime without timezone (e.g., "2026-08-10T10:00" or "2026-08-10T10:00:00")
            this.contains("T") -> {
                // Extract just the date part before 'T'
                val datePart = this.substringBefore("T")
                LocalDate.parse(datePart)
            }
            // Handle date-only format (e.g., "2025-12-17")
            else -> LocalDate.parse(this)
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
    // Handle NaN and Infinity
    if (this.isNaN() || this.isInfinite()) return "0.00"

    // Round to 2 decimal places using proper rounding
    val cents = kotlin.math.round(kotlin.math.abs(this) * 100).toLong()

    // Split into integer and decimal parts
    val integerPart = cents / 100
    val decimalPart = kotlin.math.abs(cents % 100).toInt()

    // Add thousand separators to integer part
    val formattedInteger = integerPart.toString().reversed().chunked(3).joinToString(",").reversed()

    // Format decimal part with leading zero if needed
    val formattedDecimal = decimalPart.toString().padStart(2, '0')

    val result = "$formattedInteger.$formattedDecimal"
    return if (this < 0) "-$result" else result
}

fun Double.formatBalance(currencySymbol: String): String {
    // Handle NaN and Infinity
    if (this.isNaN() || this.isInfinite()) return "${currencySymbol}0.00"

    // Round to 2 decimal places using proper rounding
    val cents = kotlin.math.round(kotlin.math.abs(this) * 100).toLong()

    // Split into integer and decimal parts
    val integerPart = cents / 100
    val decimalPart = kotlin.math.abs(cents % 100).toInt()

    // Add thousand separators to integer part
    val formattedInteger = integerPart.toString().reversed().chunked(3).joinToString(",").reversed()

    // Format decimal part with leading zero if needed
    val formattedDecimal = decimalPart.toString().padStart(2, '0')

    val result = "$formattedInteger.$formattedDecimal"
    return if (this < 0) "-$currencySymbol$result" else "$currencySymbol$result"
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

        "$displayHour:$minute"
    } catch (e: Exception) {
        this
    }
}

fun String.toLocalDateSafe(): LocalDate? {
    return try {
        // Try parsing as ISO 8601 timestamp first (e.g., "2025-07-25T15:17:00")
        if (this.contains("T")) {
            val instant = Instant.parse(this)
            instant.toLocalDateTime(TimeZone.currentSystemDefault()).date
        } else {
            // Parse as date-only format (e.g., "2025-07-25")
            LocalDate.parse(this)
        }
    } catch (e: Exception) {
        null
    }
}

fun String.toCompactDateFormat(): String {
    return try {
        val date = this.toLocalDateSafe() ?: return this
        "${date.month.name.take(3)} ${date.dayOfMonth}, ${date.year}"
    } catch (e: Exception) {
        this
    }
}

fun String.toFormattedDate(): String {
    return try {
        val date = if (this.contains("T")) {
            // Parse as ISO 8601 datetime (e.g., "2026-08-03T10:00:00")
            val instant = Instant.parse(this)
            instant.toLocalDateTime(TimeZone.currentSystemDefault()).date
        } else {
            // Parse as date-only format (e.g., "2026-08-03")
            LocalDate.parse(this)
        }

        val formatter = LocalDate.Format {
            monthName(MonthNames.ENGLISH_FULL)
            char(' ')
            dayOfMonth(padding = Padding.NONE)
            char(' ')
            year()
        }

        date.format(formatter)
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

