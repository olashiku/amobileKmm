package com.exquisite.a_mobile_kmm.core.camera

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.refTo
import platform.AVFoundation.AVAuthorizationStatusAuthorized
import platform.AVFoundation.AVAuthorizationStatusDenied
import platform.AVFoundation.AVAuthorizationStatusNotDetermined
import platform.AVFoundation.AVAuthorizationStatusRestricted
import platform.AVFoundation.AVCaptureDevice
import platform.AVFoundation.AVMediaTypeVideo
import platform.AVFoundation.authorizationStatusForMediaType
import platform.AVFoundation.requestAccessForMediaType
import platform.UIKit.UIApplication
import platform.UIKit.UIImage
import platform.UIKit.UIImageJPEGRepresentation
import platform.UIKit.UIImagePickerController
import platform.UIKit.UIImagePickerControllerCameraCaptureMode
import platform.UIKit.UIImagePickerControllerCameraDevice
import platform.UIKit.UIImagePickerControllerDelegateProtocol
import platform.UIKit.UIImagePickerControllerSourceType
import platform.UIKit.UINavigationControllerDelegateProtocol
import platform.darwin.NSObject
import platform.posix.memcpy

@OptIn(ExperimentalForeignApi::class)
@Composable
actual fun rememberCameraLauncher(
    onResult: (ByteArray?) -> Unit
): CameraLauncher {
    return remember {
        CameraLauncherImpl(onResult)
    }
}

@OptIn(ExperimentalForeignApi::class)
private class CameraLauncherImpl(
    private val onResult: (ByteArray?) -> Unit
) : CameraLauncher {

    // Store both picker and delegate to prevent premature deallocation
    private var pickerRef: UIImagePickerController? = null
    private var delegateRef: CameraPickerDelegate? = null

    override fun launch() {
        // Check camera permission status
        val authStatus = AVCaptureDevice.authorizationStatusForMediaType(AVMediaTypeVideo)

        when (authStatus) {
            AVAuthorizationStatusAuthorized -> {
                // Permission already granted, launch camera
                launchCameraPicker()
            }
            AVAuthorizationStatusNotDetermined -> {
                // Permission not requested yet, request it
                AVCaptureDevice.requestAccessForMediaType(AVMediaTypeVideo) { granted ->
                    if (granted) {
                        launchCameraPicker()
                    } else {
                        println("Camera permission denied")
                        onResult(null)
                    }
                }
            }
            AVAuthorizationStatusDenied, AVAuthorizationStatusRestricted -> {
                // Permission denied or restricted
                println("Camera permission denied or restricted. Please enable in Settings.")
                onResult(null)
            }
            else -> {
                println("Unknown camera authorization status")
                onResult(null)
            }
        }
    }

    private fun launchCameraPicker() {
        // Check if camera is available
        if (!UIImagePickerController.isSourceTypeAvailable(
                UIImagePickerControllerSourceType.UIImagePickerControllerSourceTypeCamera
            )
        ) {
            println("Camera not available on this device")
            onResult(null)
            return
        }

        try {
            val picker = UIImagePickerController()
            picker.sourceType = UIImagePickerControllerSourceType.UIImagePickerControllerSourceTypeCamera
            picker.allowsEditing = false
            picker.cameraCaptureMode = UIImagePickerControllerCameraCaptureMode.UIImagePickerControllerCameraCaptureModePhoto
            picker.cameraDevice = UIImagePickerControllerCameraDevice.UIImagePickerControllerCameraDeviceRear
            picker.showsCameraControls = true

            // Explicitly set media types to photo only
            picker.setMediaTypes(listOf("public.image"))

            // Create and retain delegate
            val delegate = CameraPickerDelegate { result ->
                // Clear references after completion
                this.pickerRef = null
                this.delegateRef = null
                onResult(result)
            }

            this.delegateRef = delegate
            this.pickerRef = picker

            picker.delegate = delegate

            // Get the root view controller and present the picker on main thread
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
            println("Error launching camera: ${e.message}")
            this.pickerRef = null
            this.delegateRef = null
            onResult(null)
        }
    }

    override fun requestPermission() {
        // Request camera permission upfront for smoother UX
        val authStatus = AVCaptureDevice.authorizationStatusForMediaType(AVMediaTypeVideo)

        if (authStatus == AVAuthorizationStatusNotDetermined) {
            AVCaptureDevice.requestAccessForMediaType(AVMediaTypeVideo) { granted ->
                if (granted) {
                    println("Camera permission granted")
                } else {
                    println("Camera permission denied")
                }
            }
        }
    }
}

@OptIn(ExperimentalForeignApi::class)
private class CameraPickerDelegate(
    private val onComplete: (ByteArray?) -> Unit
) : NSObject(),
    UIImagePickerControllerDelegateProtocol,
    UINavigationControllerDelegateProtocol {

    override fun imagePickerController(
        picker: UIImagePickerController,
        didFinishPickingMediaWithInfo: Map<Any?, *>
    ) {
        picker.dismissViewControllerAnimated(true) {
            val image = didFinishPickingMediaWithInfo["UIImagePickerControllerOriginalImage"] as? UIImage

            if (image != null) {
                // Convert UIImage to JPEG data
                val imageData = UIImageJPEGRepresentation(image, 0.8)

                if (imageData != null) {
                    try {
                        // Convert NSData to ByteArray
                        val bytes = ByteArray(imageData.length.toInt())
                        memcpy(bytes.refTo(0), imageData.bytes, imageData.length)
                        onComplete(bytes)
                    } catch (e: Exception) {
                        println("Error converting image: ${e.message}")
                        onComplete(null)
                    }
                } else {
                    println("Failed to convert UIImage to JPEG")
                    onComplete(null)
                }
            } else {
                println("No image found in media info")
                onComplete(null)
            }
        }
    }

    override fun imagePickerControllerDidCancel(picker: UIImagePickerController) {
        picker.dismissViewControllerAnimated(true) {
            onComplete(null)
        }
    }
}
