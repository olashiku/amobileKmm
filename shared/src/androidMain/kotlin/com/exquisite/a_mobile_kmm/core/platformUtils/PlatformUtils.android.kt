package com.exquisite.a_mobile_kmm.core.platformUtils

import android.content.Context
import android.content.Intent
import android.net.Uri
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

actual fun openUrl(url: String) {
    val context = KoinContextProvider.getsContext()
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url)).apply {
        flags = Intent.FLAG_ACTIVITY_NEW_TASK
    }
    context.startActivity(intent)
}

object KoinContextProvider : KoinComponent {
    private val context: Context by inject()

    fun getsContext(): Context = context
}
