package com.exquisite.a_mobile_kmm.core.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// Material Design 3 Typography Scale with Poppins Font
@Composable
fun getDrippTypography(): Typography {
    val poppinsFontFamily = getPoppinsFontFamilyPlatform()

    return Typography(
        displayLarge = TextStyle(
            fontFamily = poppinsFontFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 57.sp,
            lineHeight = 64.sp,
            letterSpacing = (-0.25).sp,
        ),
        displayMedium = TextStyle(
            fontFamily = poppinsFontFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 45.sp,
            lineHeight = 52.sp,
            letterSpacing = 0.sp,
        ),
        displaySmall = TextStyle(
            fontFamily = poppinsFontFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 24.sp,
            lineHeight = 44.sp,
            letterSpacing = 0.sp,
        ),

        // Headline styles - for section headers, product names
        headlineLarge = TextStyle(
            fontFamily = poppinsFontFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 32.sp,
            lineHeight = 40.sp,
            letterSpacing = 0.sp,
        ),
        headlineMedium = TextStyle(
            fontFamily = poppinsFontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 28.sp,
            lineHeight = 36.sp,
            letterSpacing = 0.sp,
        ),
        headlineSmall = TextStyle(
            fontFamily = poppinsFontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 24.sp,
            lineHeight = 32.sp,
            letterSpacing = 0.sp,
        ),

        // Title styles - for card titles, product prices
        titleLarge = TextStyle(
            fontFamily = poppinsFontFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 22.sp,
            lineHeight = 28.sp,
            letterSpacing = 0.sp,
        ),
        titleMedium = TextStyle(
            fontFamily = poppinsFontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            lineHeight = 24.sp,
            letterSpacing = 0.15.sp,
        ),
        titleSmall = TextStyle(
            fontFamily = poppinsFontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
            lineHeight = 20.sp,
            letterSpacing = 0.1.sp,
        ),

        // Body styles - for product descriptions, content
        bodyLarge = TextStyle(
            fontFamily = poppinsFontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            lineHeight = 24.sp,
            letterSpacing = 0.5.sp,
        ),
        bodyMedium = TextStyle(
            fontFamily = poppinsFontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
            lineHeight = 20.sp,
            letterSpacing = 0.25.sp,
        ),
        bodySmall = TextStyle(
            fontFamily = poppinsFontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp,
            lineHeight = 16.sp,
            letterSpacing = 0.4.sp,
        ),

        // Label styles - for buttons, tags, navigation
        labelLarge = TextStyle(
            fontFamily = poppinsFontFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 14.sp,
            lineHeight = 20.sp,
            letterSpacing = 0.1.sp,
        ),
        labelMedium = TextStyle(
            fontFamily = poppinsFontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 12.sp,
            lineHeight = 16.sp,
            letterSpacing = 0.5.sp,
        ),
        labelSmall = TextStyle(
            fontFamily = poppinsFontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 11.sp,
            lineHeight = 16.sp,
            letterSpacing = 0.5.sp,
        ),
    )
}

// ============================================================================
// POPPINS TYPOGRAPHY SYSTEM
// Complete font weight and size combinations for professional use
// ============================================================================

// MARK: - Font Weight Helpers

/**
 * Creates a Poppins text style with the specified weight and size
 * @param weight Font weight (Thin, Light, Regular, Medium, SemiBold, Bold, ExtraBold, Black)
 * @param fontSize Font size in sp
 * @param lineHeight Line height in sp (defaults to fontSize * 1.4)
 * @param letterSpacing Letter spacing in sp (defaults to 0)
 */
@Composable
fun poppinsTextStyle(
    weight: FontWeight,
    fontSize: Int,
    lineHeight: Int = (fontSize * 1.4).toInt(),
    letterSpacing: Float = 0f
): TextStyle {
    return TextStyle(
        fontFamily = getPoppinsFontFamilyPlatform(),
        fontWeight = weight,
        fontSize = fontSize.sp,
        lineHeight = lineHeight.sp,
        letterSpacing = letterSpacing.sp,
    )
}

// MARK: - Thin (100)

@Composable fun getPoppinsThin10() = poppinsTextStyle(FontWeight.Thin, 10, 14)
@Composable fun getPoppinsThin12() = poppinsTextStyle(FontWeight.Thin, 12, 16)
@Composable fun getPoppinsThin14() = poppinsTextStyle(FontWeight.Thin, 14, 20)
@Composable fun getPoppinsThin16() = poppinsTextStyle(FontWeight.Thin, 16, 22)
@Composable fun getPoppinsThin18() = poppinsTextStyle(FontWeight.Thin, 18, 26)
@Composable fun getPoppinsThin20() = poppinsTextStyle(FontWeight.Thin, 20, 28)
@Composable fun getPoppinsThin24() = poppinsTextStyle(FontWeight.Thin, 24, 32)
@Composable fun getPoppinsThin28() = poppinsTextStyle(FontWeight.Thin, 28, 36)
@Composable fun getPoppinsThin32() = poppinsTextStyle(FontWeight.Thin, 32, 40)
@Composable fun getPoppinsThin36() = poppinsTextStyle(FontWeight.Thin, 36, 44)

// MARK: - ExtraLight (200)

@Composable fun getPoppinsExtraLight10() = poppinsTextStyle(FontWeight.ExtraLight, 10, 14)
@Composable fun getPoppinsExtraLight12() = poppinsTextStyle(FontWeight.ExtraLight, 12, 16)
@Composable fun getPoppinsExtraLight14() = poppinsTextStyle(FontWeight.ExtraLight, 14, 20)
@Composable fun getPoppinsExtraLight16() = poppinsTextStyle(FontWeight.ExtraLight, 16, 22)
@Composable fun getPoppinsExtraLight18() = poppinsTextStyle(FontWeight.ExtraLight, 18, 26)
@Composable fun getPoppinsExtraLight20() = poppinsTextStyle(FontWeight.ExtraLight, 20, 28)
@Composable fun getPoppinsExtraLight24() = poppinsTextStyle(FontWeight.ExtraLight, 24, 32)
@Composable fun getPoppinsExtraLight28() = poppinsTextStyle(FontWeight.ExtraLight, 28, 36)
@Composable fun getPoppinsExtraLight32() = poppinsTextStyle(FontWeight.ExtraLight, 32, 40)
@Composable fun getPoppinsExtraLight36() = poppinsTextStyle(FontWeight.ExtraLight, 36, 44)

// MARK: - Light (300)

@Composable fun getPoppinsLight10() = poppinsTextStyle(FontWeight.Light, 10, 14)
@Composable fun getPoppinsLight12() = poppinsTextStyle(FontWeight.Light, 12, 16)
@Composable fun getPoppinsLight14() = poppinsTextStyle(FontWeight.Light, 14, 20)
@Composable fun getPoppinsLight16() = poppinsTextStyle(FontWeight.Light, 16, 22)
@Composable fun getPoppinsLight18() = poppinsTextStyle(FontWeight.Light, 18, 26)
@Composable fun getPoppinsLight20() = poppinsTextStyle(FontWeight.Light, 20, 28)
@Composable fun getPoppinsLight24() = poppinsTextStyle(FontWeight.Light, 24, 32)
@Composable fun getPoppinsLight28() = poppinsTextStyle(FontWeight.Light, 28, 36)
@Composable fun getPoppinsLight32() = poppinsTextStyle(FontWeight.Light, 32, 40)
@Composable fun getPoppinsLight36() = poppinsTextStyle(FontWeight.Light, 36, 44)

// MARK: - Regular (400)

@Composable fun getPoppinsRegular10() = poppinsTextStyle(FontWeight.Normal, 10, 14)
@Composable fun getPoppinsRegular11() = poppinsTextStyle(FontWeight.Normal, 11, 16)
@Composable fun getPoppinsRegular12() = poppinsTextStyle(FontWeight.Normal, 12, 16)
@Composable fun getPoppinsRegular14() = poppinsTextStyle(FontWeight.Normal, 14, 20)
@Composable fun getPoppinsRegular16() = poppinsTextStyle(FontWeight.Normal, 16, 22)
@Composable fun getPoppinsRegular18() = poppinsTextStyle(FontWeight.Normal, 18, 26)
@Composable fun getPoppinsRegular20() = poppinsTextStyle(FontWeight.Normal, 20, 28)
@Composable fun getPoppinsRegular24() = poppinsTextStyle(FontWeight.Normal, 24, 32)
@Composable fun getPoppinsRegular28() = poppinsTextStyle(FontWeight.Normal, 28, 36)
@Composable fun getPoppinsRegular32() = poppinsTextStyle(FontWeight.Normal, 32, 40)
@Composable fun getPoppinsRegular36() = poppinsTextStyle(FontWeight.Normal, 36, 44)
@Composable fun getPoppinsRegular40() = poppinsTextStyle(FontWeight.Normal, 40, 48)
@Composable fun getPoppinsRegular48() = poppinsTextStyle(FontWeight.Normal, 48, 56)

// MARK: - Medium (500)

@Composable fun getPoppinsMedium10() = poppinsTextStyle(FontWeight.Medium, 10, 14)
@Composable fun getPoppinsMedium12() = poppinsTextStyle(FontWeight.Medium, 12, 16)
@Composable fun getPoppinsMedium13() = poppinsTextStyle(FontWeight.Medium, 13, 16)
@Composable fun getPoppinsMedium14() = poppinsTextStyle(FontWeight.Medium, 14, 20)
@Composable fun getPoppinsMedium16() = poppinsTextStyle(FontWeight.Medium, 16, 22)
@Composable fun getPoppinsMedium18() = poppinsTextStyle(FontWeight.Medium, 18, 26)
@Composable fun getPoppinsMedium20() = poppinsTextStyle(FontWeight.Medium, 20, 28)
@Composable fun getPoppinsMedium24() = poppinsTextStyle(FontWeight.Medium, 24, 32)
@Composable fun getPoppinsMedium28() = poppinsTextStyle(FontWeight.Medium, 28, 36)
@Composable fun getPoppinsMedium32() = poppinsTextStyle(FontWeight.Medium, 32, 40)
@Composable fun getPoppinsMedium36() = poppinsTextStyle(FontWeight.Medium, 36, 44)
@Composable fun getPoppinsMedium40() = poppinsTextStyle(FontWeight.Medium, 40, 48)
@Composable fun getPoppinsMedium48() = poppinsTextStyle(FontWeight.Medium, 48, 56)

// MARK: - SemiBold (600)

@Composable fun getPoppinsSemiBold10() = poppinsTextStyle(FontWeight.SemiBold, 10, 14)
@Composable fun getPoppinsSemiBold12() = poppinsTextStyle(FontWeight.SemiBold, 12, 16)
@Composable fun getPoppinsSemiBold13() = poppinsTextStyle(FontWeight.SemiBold, 13, 16)

@Composable fun getPoppinsSemiBold14() = poppinsTextStyle(FontWeight.SemiBold, 14, 20)
@Composable fun getPoppinsSemiBold16() = poppinsTextStyle(FontWeight.SemiBold, 16, 22)
@Composable fun getPoppinsSemiBold18() = poppinsTextStyle(FontWeight.SemiBold, 18, 26)
@Composable fun getPoppinsSemiBold20() = poppinsTextStyle(FontWeight.SemiBold, 20, 28)
@Composable fun getPoppinsSemiBold24() = poppinsTextStyle(FontWeight.SemiBold, 24, 32)
@Composable fun getPoppinsSemiBold28() = poppinsTextStyle(FontWeight.SemiBold, 28, 36)
@Composable fun getPoppinsSemiBold32() = poppinsTextStyle(FontWeight.SemiBold, 32, 40)
@Composable fun getPoppinsSemiBold36() = poppinsTextStyle(FontWeight.SemiBold, 36, 44)
@Composable fun getPoppinsSemiBold40() = poppinsTextStyle(FontWeight.SemiBold, 40, 48)
@Composable fun getPoppinsSemiBold48() = poppinsTextStyle(FontWeight.SemiBold, 48, 56)

// MARK: - Bold (700)

@Composable fun getPoppinsBold10() = poppinsTextStyle(FontWeight.Bold, 10, 14)
@Composable fun getPoppinsBold11() = poppinsTextStyle(FontWeight.Bold, 11, 16)
@Composable fun getPoppinsBold12() = poppinsTextStyle(FontWeight.Bold, 12, 16)
@Composable fun getPoppinsBold14() = poppinsTextStyle(FontWeight.Bold, 14, 20)
@Composable fun getPoppinsBold15() = poppinsTextStyle(FontWeight.Bold, 15, 22)
@Composable fun getPoppinsBold16() = poppinsTextStyle(FontWeight.Bold, 16, 22)
@Composable fun getPoppinsBold18() = poppinsTextStyle(FontWeight.Bold, 18, 26)
@Composable fun getPoppinsBold20() = poppinsTextStyle(FontWeight.Bold, 20, 28)
@Composable fun getPoppinsBold24() = poppinsTextStyle(FontWeight.Bold, 24, 32)
@Composable fun getPoppinsBold28() = poppinsTextStyle(FontWeight.Bold, 28, 36)
@Composable fun getPoppinsBold32() = poppinsTextStyle(FontWeight.Bold, 32, 40)
@Composable fun getPoppinsBold36() = poppinsTextStyle(FontWeight.Bold, 36, 44)
@Composable fun getPoppinsBold40() = poppinsTextStyle(FontWeight.Bold, 40, 48)
@Composable fun getPoppinsBold48() = poppinsTextStyle(FontWeight.Bold, 48, 56)

// MARK: - ExtraBold (800)

@Composable fun getPoppinsExtraBold10() = poppinsTextStyle(FontWeight.ExtraBold, 10, 14)
@Composable fun getPoppinsExtraBold12() = poppinsTextStyle(FontWeight.ExtraBold, 12, 16)
@Composable fun getPoppinsExtraBold14() = poppinsTextStyle(FontWeight.ExtraBold, 14, 20)
@Composable fun getPoppinsExtraBold16() = poppinsTextStyle(FontWeight.ExtraBold, 16, 22)
@Composable fun getPoppinsExtraBold18() = poppinsTextStyle(FontWeight.ExtraBold, 18, 26)
@Composable fun getPoppinsExtraBold20() = poppinsTextStyle(FontWeight.ExtraBold, 20, 28)
@Composable fun getPoppinsExtraBold24() = poppinsTextStyle(FontWeight.ExtraBold, 24, 32)
@Composable fun getPoppinsExtraBold28() = poppinsTextStyle(FontWeight.ExtraBold, 28, 36)
@Composable fun getPoppinsExtraBold32() = poppinsTextStyle(FontWeight.ExtraBold, 32, 40)
@Composable fun getPoppinsExtraBold36() = poppinsTextStyle(FontWeight.ExtraBold, 36, 44)
@Composable fun getPoppinsExtraBold40() = poppinsTextStyle(FontWeight.ExtraBold, 40, 48)
@Composable fun getPoppinsExtraBold48() = poppinsTextStyle(FontWeight.ExtraBold, 48, 56)

// MARK: - Black (900)

@Composable fun getPoppinsBlack10() = poppinsTextStyle(FontWeight.Black, 10, 14)
@Composable fun getPoppinsBlack12() = poppinsTextStyle(FontWeight.Black, 12, 16)
@Composable fun getPoppinsBlack14() = poppinsTextStyle(FontWeight.Black, 14, 20)
@Composable fun getPoppinsBlack16() = poppinsTextStyle(FontWeight.Black, 16, 22)
@Composable fun getPoppinsBlack18() = poppinsTextStyle(FontWeight.Black, 18, 26)
@Composable fun getPoppinsBlack20() = poppinsTextStyle(FontWeight.Black, 20, 28)
@Composable fun getPoppinsBlack24() = poppinsTextStyle(FontWeight.Black, 24, 32)
@Composable fun getPoppinsBlack28() = poppinsTextStyle(FontWeight.Black, 28, 36)
@Composable fun getPoppinsBlack32() = poppinsTextStyle(FontWeight.Black, 32, 40)
@Composable fun getPoppinsBlack36() = poppinsTextStyle(FontWeight.Black, 36, 44)
@Composable fun getPoppinsBlack40() = poppinsTextStyle(FontWeight.Black, 40, 48)
@Composable fun getPoppinsBlack48() = poppinsTextStyle(FontWeight.Black, 48, 56)

// ============================================================================
// SEMANTIC TEXT STYLES
// Pre-defined styles for common UI patterns
// ============================================================================

object AppTypography {
    // Headings
    @Composable fun heading1() = getPoppinsBold32()
    @Composable fun heading2() = getPoppinsBold28()
    @Composable fun heading3() = getPoppinsSemiBold24()
    @Composable fun heading4() = getPoppinsSemiBold20()
    @Composable fun heading5() = getPoppinsSemiBold18()
    @Composable fun heading6() = getPoppinsSemiBold16()

    // Body Text
    @Composable fun bodyLarge() = getPoppinsRegular16()
    @Composable fun bodyMedium() = getPoppinsRegular14()
    @Composable fun bodySmall() = getPoppinsRegular12()

    // Labels & Buttons
    @Composable fun buttonLarge() = getPoppinsSemiBold16()
    @Composable fun buttonMedium() = getPoppinsSemiBold14()
    @Composable fun buttonSmall() = getPoppinsSemiBold12()

    // Captions & Helper Text
    @Composable fun caption() = getPoppinsRegular12()
    @Composable fun captionBold() = getPoppinsSemiBold12()
    @Composable fun overline() = getPoppinsMedium10()

    // Special Purpose
    @Composable fun price() = getPoppinsSemiBold20()
    @Composable fun productTitle() = getPoppinsSemiBold18()
    @Composable fun brandName() = getPoppinsBold36()
    @Composable fun sectionHeader() = getPoppinsSemiBold16()
}