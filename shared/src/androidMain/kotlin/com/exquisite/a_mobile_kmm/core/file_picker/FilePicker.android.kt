package com.exquisite.a_mobile_kmm.core.file_picker

import android.provider.OpenableColumns
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext

@Composable
actual fun rememberFilePicker(
    allowedExtensions: List<String>,
    onResult: (FilePickerResult?) -> Unit
): FilePicker {
    val context = LocalContext.current

    // File picker launcher
    val filePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument()
    ) { uri ->
        if (uri != null) {
            try {
                // Read file content
                val bytes = context.contentResolver.openInputStream(uri)?.use {
                    it.readBytes()
                }

                // Get file name and MIME type
                var fileName = "unknown"
                var mimeType: String? = null

                context.contentResolver.query(uri, null, null, null, null)?.use { cursor ->
                    if (cursor.moveToFirst()) {
                        val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                        if (nameIndex != -1) {
                            fileName = cursor.getString(nameIndex)
                        }
                    }
                }

                mimeType = context.contentResolver.getType(uri)

                if (bytes != null) {
                    onResult(FilePickerResult(bytes, fileName, mimeType))
                } else {
                    onResult(null)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                onResult(null)
            }
        } else {
            onResult(null)
        }
    }

    return remember {
        object : FilePicker {
            override fun launch() {
                // Build MIME types array based on allowed extensions
                val mimeTypes = if (allowedExtensions.isNotEmpty()) {
                    allowedExtensions.mapNotNull { extension ->
                        when (extension.lowercase()) {
                            "pdf" -> "application/pdf"
                            "doc" -> "application/msword"
                            "docx" -> "application/vnd.openxmlformats-officedocument.wordprocessingml.document"
                            "txt" -> "text/plain"
                            "jpg", "jpeg" -> "image/jpeg"
                            "png" -> "image/png"
                            else -> null
                        }
                    }.toTypedArray()
                } else {
                    arrayOf("*/*")
                }

                filePickerLauncher.launch(mimeTypes)
            }
        }
    }
}
