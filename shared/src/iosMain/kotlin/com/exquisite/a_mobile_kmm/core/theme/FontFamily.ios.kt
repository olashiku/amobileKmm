package com.exquisite.a_mobile_kmm.core.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import amobilekmm.shared.generated.resources.Res
import amobilekmm.shared.generated.resources.Poppins_Bold
import amobilekmm.shared.generated.resources.Poppins_Light
import amobilekmm.shared.generated.resources.Poppins_Medium
import amobilekmm.shared.generated.resources.Poppins_Regular
import amobilekmm.shared.generated.resources.Poppins_SemiBold
import org.jetbrains.compose.resources.Font

@Composable
actual fun getPoppinsFontFamilyPlatform() = FontFamily(
    Font(Res.font.Poppins_Light, FontWeight.Light),         // 300
    Font(Res.font.Poppins_Regular, FontWeight.Normal),      // 400
    Font(Res.font.Poppins_Medium, FontWeight.Medium),       // 500
    Font(Res.font.Poppins_SemiBold, FontWeight.SemiBold),   // 600
    Font(Res.font.Poppins_Bold, FontWeight.Bold),           // 700
)
