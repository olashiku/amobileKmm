package com.exquisite.a_mobile_kmm.core.screen_components

/**
 * Example usage of MediaSourceDialog
 *
 * // For image only (camera + gallery):
 * MediaSourceDialog(
 *     onDismiss = { showDialog = false },
 *     title = "Upload Photo",
 *     showCamera = true,
 *     showGallery = true,
 *     showDocument = false,
 *     onCameraSelected = { cameraLauncher.launch() },
 *     onGallerySelected = { imagePickerLauncher.launch() }
 * )
 *
 * // For documents only:
 * MediaSourceDialog(
 *     onDismiss = { showDialog = false },
 *     title = "Upload Document",
 *     showCamera = false,
 *     showGallery = false,
 *     showDocument = true,
 *     documentButtonText = "Choose PDF/DOCX",
 *     onDocumentSelected = { filePickerLauncher.launch() }
 * )
 *
 * // For both images and documents:
 * MediaSourceDialog(
 *     onDismiss = { showDialog = false },
 *     title = "Upload File",
 *     showCamera = true,
 *     showGallery = true,
 *     showDocument = true,
 *     onCameraSelected = { cameraLauncher.launch() },
 *     onGallerySelected = { imagePickerLauncher.launch() },
 *     onDocumentSelected = { filePickerLauncher.launch() }
 * )
 *
 * // Complete example with file picker:
 * val filePickerLauncher = rememberFilePicker(
 *     allowedExtensions = listOf("pdf", "doc", "docx"),
 *     onResult = { fileResult ->
 *         fileResult?.let {
 *             // it.data is ByteArray
 *             // it.fileName is String
 *             // it.mimeType is String?
 *             uploadFile(it.data, it.fileName)
 *         }
 *     }
 * )
 */
