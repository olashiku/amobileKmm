package com.exquisite.a_mobile_kmm.core.file_picker

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.refTo
import platform.Foundation.NSData
import platform.Foundation.NSURL
import platform.Foundation.dataWithContentsOfURL
import platform.UIKit.UIApplication
import platform.UIKit.UIDocumentPickerDelegateProtocol
import platform.UIKit.UIDocumentPickerViewController
import platform.UniformTypeIdentifiers.UTType
import platform.UniformTypeIdentifiers.UTTypePDF
import platform.UniformTypeIdentifiers.UTTypePlainText
import platform.UniformTypeIdentifiers.UTTypePNG
import platform.UniformTypeIdentifiers.UTTypeJPEG
import platform.darwin.NSObject
import platform.posix.memcpy

@OptIn(ExperimentalForeignApi::class)
@Composable
actual fun rememberFilePicker(
    allowedExtensions: List<String>,
    onResult: (FilePickerResult?) -> Unit
): FilePicker {
    return remember {
        FilePickerImpl(allowedExtensions, onResult)
    }
}

@OptIn(ExperimentalForeignApi::class)
private class FilePickerImpl(
    private val allowedExtensions: List<String>,
    private val onResult: (FilePickerResult?) -> Unit
) : FilePicker {

    private var pickerRef: UIDocumentPickerViewController? = null
    private var delegateRef: DocumentPickerDelegate? = null

    override fun launch() {
        try {
            // Map extensions to UTTypes
            val allowedTypes = if (allowedExtensions.isNotEmpty()) {
                allowedExtensions.mapNotNull { extension ->
                    when (extension.lowercase()) {
                        "pdf" -> UTTypePDF
                        "txt" -> UTTypePlainText
                        "png" -> UTTypePNG
                        "jpg", "jpeg" -> UTTypeJPEG
                        "doc", "docx" -> UTType.Companion.typeWithIdentifier("com.microsoft.word.doc")
                        else -> null
                    }
                }
            } else {
                listOf(UTType.Companion.typeWithIdentifier("public.data"))
            }

            if (allowedTypes.isEmpty()) {
                onResult(null)
                return
            }

            val picker = UIDocumentPickerViewController(
                forOpeningContentTypes = allowedTypes,
                asCopy = true
            )

            val delegate = DocumentPickerDelegate { result ->
                this.pickerRef = null
                this.delegateRef = null
                onResult(result)
            }

            this.delegateRef = delegate
            this.pickerRef = picker

            picker.delegate = delegate
            picker.allowsMultipleSelection = false

            val rootViewController = UIApplication.sharedApplication.keyWindow?.rootViewController
            if (rootViewController != null) {
                rootViewController.presentViewController(picker, animated = true, completion = null)
            } else {
                println("No root view controller found")
                this.pickerRef = null
                this.delegateRef = null
                onResult(null)
            }
        } catch (e: Exception) {
            println("Error launching document picker: ${e.message}")
            this.pickerRef = null
            this.delegateRef = null
            onResult(null)
        }
    }
}

@OptIn(ExperimentalForeignApi::class)
private class DocumentPickerDelegate(
    private val onComplete: (FilePickerResult?) -> Unit
) : NSObject(), UIDocumentPickerDelegateProtocol {

    override fun documentPicker(
        controller: UIDocumentPickerViewController,
        didPickDocumentsAtURLs: List<*>
    ) {
        controller.dismissViewControllerAnimated(true) {
            val url = didPickDocumentsAtURLs.firstOrNull() as? NSURL

            if (url != null) {
                try {
                    // Start accessing security-scoped resource
                    url.startAccessingSecurityScopedResource()

                    // Read file data
                    val data = NSData.dataWithContentsOfURL(url)

                    // Stop accessing security-scoped resource
                    url.stopAccessingSecurityScopedResource()

                    if (data != null) {
                        // Convert NSData to ByteArray
                        val bytes = ByteArray(data.length.toInt())
                        memcpy(bytes.refTo(0), data.bytes, data.length)

                        // Get file name
                        val fileName = url.lastPathComponent ?: "unknown"

                        // Get MIME type (simplified)
                        val mimeType = url.pathExtension?.let { extension ->
                            when (extension.lowercase()) {
                                "pdf" -> "application/pdf"
                                "doc" -> "application/msword"
                                "docx" -> "application/vnd.openxmlformats-officedocument.wordprocessingml.document"
                                "txt" -> "text/plain"
                                "jpg", "jpeg" -> "image/jpeg"
                                "png" -> "image/png"
                                else -> null
                            }
                        }

                        onComplete(FilePickerResult(bytes, fileName, mimeType))
                    } else {
                        println("Failed to read file data")
                        onComplete(null)
                    }
                } catch (e: Exception) {
                    println("Error reading file: ${e.message}")
                    url.stopAccessingSecurityScopedResource()
                    onComplete(null)
                }
            } else {
                println("No URL found")
                onComplete(null)
            }
        }
    }

    override fun documentPickerWasCancelled(controller: UIDocumentPickerViewController) {
        controller.dismissViewControllerAnimated(true) {
            onComplete(null)
        }
    }
}
