package com.exquisite.a_mobile_kmm.core.utils

import platform.Foundation.NSURL
import platform.UIKit.UIApplication

actual fun dialNumber(number: String) {
    val phoneNumber = number.replace(Regex("[^0-9+]"), "") // Clean the phone number
    val telURL = NSURL.URLWithString("tel://$phoneNumber")

    telURL?.let { url ->
        if (UIApplication.sharedApplication.canOpenURL(url)) {
            UIApplication.sharedApplication.openURL(
                url = url,
                options = emptyMap<Any?, Any>(),
                completionHandler = null
            )
        }
    }
}

actual fun sendMessage(number: String) {
    val phoneNumber = number.replace(Regex("[^0-9+]"), "") // Clean the phone number
    val smsURL = NSURL.URLWithString("sms:$phoneNumber")

    smsURL?.let { url ->
        if (UIApplication.sharedApplication.canOpenURL(url)) {
            UIApplication.sharedApplication.openURL(
                url = url,
                options = emptyMap<Any?, Any>(),
                completionHandler = null
            )
        }
    }
}