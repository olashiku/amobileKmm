package com.exquisite.a_mobile_kmm.core.utils

import android.content.Intent
import android.net.Uri
import com.exquisite.a_mobile_kmm.core.platformUtils.KoinContextProvider

actual fun dialNumber(number: String) {
    try {
        val context = KoinContextProvider.getsContext()
        val intent = Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse("tel:$number")
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
        context.startActivity(intent)
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

actual fun sendMessage(number: String) {
    try {
        val context = KoinContextProvider.getsContext()
        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse("sms:$number")
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
        context.startActivity(intent)
    } catch (e: Exception) {
        e.printStackTrace()
    }
}