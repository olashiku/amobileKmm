package com.exquisite.a_mobile_kmm.core.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color


@Immutable
data class CoinRoutineColorsPalette(
    val lightWarmGray: Color = Color.Unspecified,
    val paleCream: Color = Color.Unspecified,
    val coolGray: Color = Color.Unspecified,
    val titleColor: Color = Color.Unspecified,
    val subTitleColor: Color = Color.Unspecified,
    val captionColor: Color = Color.Unspecified,
    val titleLabelColor: Color = Color.Unspecified,
    val focusedBorderColor: Color = Color.Unspecified,
    val backgroundColor: Color = Color.Unspecified,
    val pagerUnselectedColor: Color = Color.Unspecified,
    val pagerSelectedColor: Color = Color.Unspecified,
    val textGray: Color = Color.Unspecified,
    val borderColor:Color = Color.Unspecified


)
val lightWarmGray = Color(color = 0xFFFAF9F7)
val paleCream = Color(color = 0xFFFFF9ED)
val coolGray = Color(color = 0xFFF3F3F5)
val titleColor = Color(color = 0xFFFFFFFF)
val captionColor = Color(color = 0xFFD4A574)
val subTitleColor = Color(color = 0xFFA0A0A0)
val titleLabelColor = Color(color = 0xFF0A0A0A)
val focusedBorderColor = Color(color = 0xFF717182)
val backgroundColor = Color(0xFF0A0A0A)
val pagerUnselectedColor = Color(color = 0xFF2D2D2D)
val textGray = Color(color = 0xFFA0A0A0)
val borderColor = Color(color = 0xFF3C3027)




val LightRoutineColorsPalette =
    CoinRoutineColorsPalette(
        lightWarmGray = lightWarmGray,
        paleCream = paleCream,
        coolGray = coolGray,
        titleColor = titleColor,
        captionColor = captionColor,
        subTitleColor = subTitleColor,
        titleLabelColor = titleLabelColor,
        focusedBorderColor = focusedBorderColor,
        backgroundColor = backgroundColor,
        pagerUnselectedColor = pagerUnselectedColor,
        textGray = textGray,
        borderColor = borderColor
    )


val LocalColorsPalette = compositionLocalOf { CoinRoutineColorsPalette() }