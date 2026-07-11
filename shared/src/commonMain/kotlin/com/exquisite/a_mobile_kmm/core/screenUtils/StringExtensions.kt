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

fun String.toTimeAgoDetailed(): String {
    return try {
        val timestamp = Instant.parse(this)
        val now = Clock.System.now()

        val diffMillis = now.toEpochMilliseconds() - timestamp.toEpochMilliseconds()
        val diffSeconds = diffMillis / 1000
        val diffMinutes = diffSeconds / 60
        val diffHours = diffMinutes / 60
        val diffDays = diffHours / 24

        when {
            diffSeconds < 10 -> "Just now"
            diffSeconds < 60 -> "${diffSeconds}s ago"
            diffMinutes < 60 -> "${diffMinutes}m ago"
            diffHours < 24 -> "${diffHours}h ago"
            diffDays < 30 -> "${diffDays}d ago"
            else -> {
                // For older dates, show the actual date
                val localDateTime = timestamp.toLocalDateTime(TimeZone.currentSystemDefault())
                "${localDateTime.dayOfMonth}/${localDateTime.monthNumber}/${localDateTime.year}"
            }
        }
    } catch (e: Exception) {
        this
    }
}

fun String.toTimeAgoFromMillis(): String {
    return try {
        // Parse the string as milliseconds timestamp
        val timestampMillis = this.toLongOrNull() ?: return this
        val timestamp = Instant.fromEpochMilliseconds(timestampMillis)
        val now = Clock.System.now()

        val diffMillis = now.toEpochMilliseconds() - timestamp.toEpochMilliseconds()
        val diffSeconds = diffMillis / 1000
        val diffMinutes = diffSeconds / 60
        val diffHours = diffMinutes / 60
        val diffDays = diffHours / 24

        when {
            diffSeconds < 10 -> "Just now"
            diffSeconds < 60 -> "${diffSeconds}s ago"
            diffMinutes < 60 -> "${diffMinutes}m ago"
            diffHours < 24 -> "${diffHours}h ago"
            diffDays < 30 -> "${diffDays}d ago"
            else -> {
                // For older dates, show the actual date
                val localDateTime = timestamp.toLocalDateTime(TimeZone.currentSystemDefault())
                "${localDateTime.dayOfMonth}/${localDateTime.monthNumber}/${localDateTime.year}"
            }
        }
    } catch (e: Exception) {
        this
    }
}


fun String.capitalizeWords(): String {
    return this.split(" ").joinToString(" ") {
        it.replaceFirstChar { char -> char.uppercase() }
    }
}

fun dateStatus(status: String, dueDate: String): String {
    return try {
        // Parse the due date string - try ISO format first, then readable format
        val parsedDueDate = try {
            // Try ISO format first (e.g., "2026-01-26")
            LocalDate.parse(dueDate)
        } catch (e: Exception) {
            // If ISO fails, try parsing readable format (e.g., "Jan 25, 2026")
            val readableFormat = LocalDate.Format {
                monthName(MonthNames.ENGLISH_ABBREVIATED)
                char(' ')
                dayOfMonth()
                chars(", ")
                year()
            }
            LocalDate.parse(dueDate, readableFormat)
        }

        val today = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date

        // If the due date has passed and status is still "pending", it's overdue
        if (parsedDueDate < today && status.equals("pending", ignoreCase = true)) {
            "Overdue"
        } else {
            status
        }
    } catch (e: Exception) {
        // If parsing fails, return the original status
        status
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

