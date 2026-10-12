package com.hosseinalipour.barnamerizi.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

private val PersianSans = FontFamily.SansSerif

val AppTypography = Typography(
    displaySmall = TextStyle(fontFamily = PersianSans, fontWeight = FontWeight.Bold, fontSize = 32.sp, lineHeight = 43.sp),
    headlineMedium = TextStyle(fontFamily = PersianSans, fontWeight = FontWeight.Bold, fontSize = 24.sp, lineHeight = 34.sp),
    titleLarge = TextStyle(fontFamily = PersianSans, fontWeight = FontWeight.Bold, fontSize = 20.sp, lineHeight = 30.sp),
    titleMedium = TextStyle(fontFamily = PersianSans, fontWeight = FontWeight.SemiBold, fontSize = 16.sp, lineHeight = 26.sp),
    bodyLarge = TextStyle(fontFamily = PersianSans, fontWeight = FontWeight.Normal, fontSize = 16.sp, lineHeight = 27.sp),
    bodyMedium = TextStyle(fontFamily = PersianSans, fontWeight = FontWeight.Normal, fontSize = 14.sp, lineHeight = 23.sp),
    labelLarge = TextStyle(fontFamily = PersianSans, fontWeight = FontWeight.SemiBold, fontSize = 14.sp, lineHeight = 20.sp),
    labelMedium = TextStyle(fontFamily = PersianSans, fontWeight = FontWeight.Medium, fontSize = 12.sp, lineHeight = 18.sp)
)
