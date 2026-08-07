package com.exquisite.a_mobile_kmm.core.utils

import android.content.Intent
import android.net.Uri
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import android.content.Context

actual fun openInBrowser(url: String) {
    try {
        BrowserLauncherHelper.openUrl(url)
    } catch (e: Exception) {
        println("Error opening browser: ${e.message}")
        e.printStackTrace()
    }
}

private object BrowserLauncherHelper : KoinComponent {
    private val context: Context by inject()

    fun openUrl(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url)).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
        context.startActivity(intent)
    }
}
