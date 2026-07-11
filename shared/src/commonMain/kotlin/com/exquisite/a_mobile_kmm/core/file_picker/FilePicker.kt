package com.exquisite.a_mobile_kmm.core.file_picker

import androidx.compose.runtime.Composable

/**
 * File picker launcher interface for selecting files/documents
 */
interface FilePicker {
    fun launch()
}

/**
 * Result data class for file picker
 */
data class FilePickerResult(
    val data: ByteArray,
    val fileName: String,
    val mimeType: String?
) {
    override fun equals(other: Any?): Boolean {
        if (this == other) return true
        if (other == null || this::class != other::class) return false

        other as FilePickerResult

        if (!data.contentEquals(other.data)) return false
        if (fileName != other.fileName) return false
        if (mimeType != other.mimeType) return false

        return true
    }

    override fun hashCode(): Int {
        var result = data.contentHashCode()
        result = 31 * result + fileName.hashCode()
        result = 31 * result + (mimeType?.hashCode() ?: 0)
        return result
    }
}

/**
 * Creates a file picker launcher that can be used to select documents
 * @param allowedExtensions List of allowed file extensions (e.g., listOf("pdf", "docx", "doc"))
 * @param onResult Callback with the selected file data, or null if cancelled
 */
@Composable
expect fun rememberFilePicker(
    allowedExtensions: List<String> = emptyList(),
    onResult: (FilePickerResult?) -> Unit
): FilePicker
