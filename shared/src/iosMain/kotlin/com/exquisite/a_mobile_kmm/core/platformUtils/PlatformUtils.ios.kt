package com.exquisite.a_mobile_kmm.core.platformUtils

import platform.Foundation.NSURL
import platform.UIKit.UIApplication

actual fun openUrl(url: String) {
    val nsUrl = NSURL.URLWithString(url) ?: return
    UIApplication.sharedApplication.openURL(nsUrl, mapOf<Any?, Any?>()) { _ ->
        // URL opened
    }
}
