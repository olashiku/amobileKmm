package com.exquisite.a_mobile_kmm.core.screenUtils

import kotlinx.datetime.Clock

/**
 * Generates a filename with proper extension based on image data
 * Detects file type from magic bytes (file signature)
 */
fun generateImageFileName(imageData: ByteArray): String {
    val timestamp = Clock.System.now().toEpochMilliseconds()
    val extension = detectImageExtension(imageData)
    return "image_$timestamp.$extension"
}

/**
 * Detects image extension from byte array magic bytes
 * @return extension (jpg, png, etc.)
 */
private fun detectImageExtension(imageData: ByteArray): String {
    return when {
        // JPEG: FF D8
        imageData.size >= 2 &&
        imageData[0] == 0xFF.toByte() &&
        imageData[1] == 0xD8.toByte() -> "jpg"

        // PNG: 89 50 4E 47
        imageData.size >= 8 &&
        imageData[0] == 0x89.toByte() &&
        imageData[1] == 0x50.toByte() &&
        imageData[2] == 0x4E.toByte() &&
        imageData[3] == 0x47.toByte() -> "png"

        // WEBP: RIFF....WEBP
        imageData.size >= 12 &&
        imageData[0] == 0x52.toByte() && // R
        imageData[1] == 0x49.toByte() && // I
        imageData[2] == 0x46.toByte() && // F
        imageData[3] == 0x46.toByte() && // F
        imageData[8] == 0x57.toByte() && // W
        imageData[9] == 0x45.toByte() && // E
        imageData[10] == 0x42.toByte() && // B
        imageData[11] == 0x50.toByte() -> "webp" // P

        // Default to jpg if unable to detect
        else -> "jpg"
    }
}
