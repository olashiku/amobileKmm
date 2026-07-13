package com.exquisite.a_mobile_kmm.core.screen_components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.exquisite.a_mobile_kmm.core.theme.LocalColorsPalette

enum class MediaSourceType {
    CAMERA,
    GALLERY,
    DOCUMENT
}

/**
 * A flexible dialog for selecting media sources (camera, gallery, or documents).
 * Configure which options to show based on your use case.
 *
 * @param onDismiss Callback when dialog is dismissed
 * @param title Dialog title (default: "Choose Source")
 * @param description Dialog description (default: "Select how you'd like to add your file:")
 * @param showCamera Whether to show camera option
 * @param showGallery Whether to show gallery option
 * @param showDocument Whether to show document picker option
 * @param cameraButtonText Text for camera button (default: "Take Photo")
 * @param galleryButtonText Text for gallery button (default: "Choose from Gallery")
 * @param documentButtonText Text for document button (default: "Choose Document")
 * @param onCameraSelected Callback when camera is selected
 * @param onGallerySelected Callback when gallery is selected
 * @param onDocumentSelected Callback when document picker is selected
 */
@Composable
fun MediaSourceDialog(
    onDismiss: () -> Unit,
    title: String = "Choose Source",
    description: String = "Select how you'd like to add your file:",
    showCamera: Boolean = true,
    showGallery: Boolean = true,
    showDocument: Boolean = false,
    cameraButtonText: String = "Take Photo",
    galleryButtonText: String = "Choose from Gallery",
    documentButtonText: String = "Choose Document",
    onCameraSelected: (() -> Unit)? = null,
    onGallerySelected: (() -> Unit)? = null,
    onDocumentSelected: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                color = Color.Black
            )
        },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = LocalColorsPalette.current.subTitleColor
                )
            }
        },
        confirmButton = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                if (showCamera && onCameraSelected != null) {
                    PrimaryButton(
                        text = cameraButtonText,
                        onClick = onCameraSelected
                    )
                }
                if (showGallery && onGallerySelected != null) {
                    LineButtonBlackText(
                        text = galleryButtonText,
                        onClick = onGallerySelected
                    )
                }
                if (showDocument && onDocumentSelected != null) {
                    LineButtonBlackText(
                        text = documentButtonText,
                        onClick = onDocumentSelected
                    )
                }
            }
        },
        containerColor = Color(0xFFFFFFFF),
        modifier = modifier
    )
}
