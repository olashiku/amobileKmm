package com.exquisite.a_mobile_kmm.core.utils

import platform.Foundation.NSURL
import platform.UIKit.UIApplication

actual fun openInBrowser(url: String) {
    try {
        val nsUrl = NSURL.URLWithString(url)
        if (nsUrl != null && UIApplication.sharedApplication.canOpenURL(nsUrl)) {
            UIApplication.sharedApplication.openURL(
                url = nsUrl,
                options = emptyMap<Any?, Any>(),
                completionHandler = { success ->
                    if (success) {
                        println("Successfully opened URL: $url")
                    } else {
                        println("Failed to open URL: $url")
                    }
                }
            )
        } else {
            println("Cannot open URL: $url")
        }
    } catch (e: Exception) {
        println("Error opening browser: ${e.message}")
        e.printStackTrace()
    }
}
