package com.example.littlelemon.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.littlelemon.R

private val MarkaziText =
    FontFamily(
        Font(R.font.markazi_text_regular, weight = FontWeight.Normal),
        Font(R.font.markazi_text_medium, weight = FontWeight.Medium),
    )

private val Karla =
    FontFamily(
        Font(R.font.karla_regular, weight = FontWeight.Normal),
        Font(R.font.karla_medium, weight = FontWeight.Medium),
        Font(R.font.karla_bold, weight = FontWeight.Bold),
        Font(R.font.karla_extra_bold, weight = FontWeight.ExtraBold),
    )

// App typography system:
// - Markazi Text: display styles
// - Karla: headings/body styles
val Typography =
    Typography(
        // Markazi Text
        displayLarge =
        TextStyle(
            fontFamily = MarkaziText,
            fontWeight = FontWeight.Medium,
            fontSize = 64.sp,
        ),
        displayMedium =
        TextStyle(
            fontFamily = MarkaziText,
            fontWeight = FontWeight.Normal,
            fontSize = 40.sp,
        ),
        // Karla
        headlineSmall =
        TextStyle(
            fontFamily = Karla,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 20.sp,
        ),
        titleMedium =
        TextStyle(
            fontFamily = Karla,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
        ),
        titleSmall =
        TextStyle(
            fontFamily = Karla,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 16.sp,
        ),
        bodyLarge =
        TextStyle(
            fontFamily = Karla,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            lineHeight = 24.sp, // 1.5 line height
        ),
        bodyMedium =
        TextStyle(
            fontFamily = Karla,
            fontWeight = FontWeight.Medium,
            fontSize = 18.sp,
        ),
        bodySmall =
        TextStyle(
            fontFamily = Karla,
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
        ),
    )
