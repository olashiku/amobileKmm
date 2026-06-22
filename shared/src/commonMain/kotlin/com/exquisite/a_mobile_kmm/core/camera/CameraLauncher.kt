package com.exquisite.a_mobile_kmm.core.camera

import androidx.compose.runtime.Composable

/**
 * Camera launcher interface for taking photos
 */
interface CameraLauncher {
    fun launch()
    fun requestPermission()
}

/**
 * Creates a camera launcher that can be used to capture images
 * @param onResult Callback with the captured image as ByteArray, or null if cancelled
 */
@Composable
expect fun rememberCameraLauncher(
    onResult: (ByteArray?) -> Unit
): CameraLauncher
