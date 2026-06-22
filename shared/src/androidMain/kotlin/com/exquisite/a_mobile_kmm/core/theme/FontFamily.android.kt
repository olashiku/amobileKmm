package com.exquisite.a_mobile_kmm.core.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight

@Composable
actual fun getPoppinsFontFamilyPlatform(): FontFamily {
    val context = LocalContext.current

    return try {
        // Load fonts from res/font directory using resource identifiers
        val resources = context.resources
        val packageName = context.packageName

        FontFamily(
            Font(
                resources.getIdentifier("poppins_light", "font", packageName),
                FontWeight.Light
            ),
            Font(
                resources.getIdentifier("poppins_regular", "font", packageName),
                FontWeight.Normal
            ),
            Font(
                resources.getIdentifier("poppins_medium", "font", packageName),
                FontWeight.Medium
            ),
            Font(
                resources.getIdentifier("poppins_semibold", "font", packageName),
                FontWeight.SemiBold
            ),
            Font(
                resources.getIdentifier("poppins_bold", "font", packageName),
                FontWeight.Bold
            ),
        )
    } catch (e: Exception) {
        // Fallback to system font if loading fails
        println("Failed to load Poppins fonts: ${e.message}")
        e.printStackTrace()
        FontFamily.SansSerif
    }
}
