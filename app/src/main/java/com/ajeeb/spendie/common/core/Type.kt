package com.ajeeb.spendie.common.core

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.ajeeb.spendie.R

@Composable
fun poppinsFontFamily() = FontFamily(
    Font(R.font.poppins_regular, FontWeight.Normal),
    Font(R.font.poppins_medium, FontWeight.Medium),
    Font(R.font.poppins_semi_bold, FontWeight.SemiBold),
    Font(R.font.poppins_bold, FontWeight.Bold),
)

@Composable
fun spendieTypography() = Typography(
    bodyLarge = TextStyle(
        fontFamily = poppinsFontFamily(), fontWeight = FontWeight.Normal, fontSize = 20.sp
    ), bodyMedium = TextStyle(
        fontFamily = poppinsFontFamily(), fontWeight = FontWeight.Normal, fontSize = 16.sp
    ), bodySmall = TextStyle(
        fontFamily = poppinsFontFamily(), fontWeight = FontWeight.Normal, fontSize = 14.sp
    ), headlineLarge = TextStyle(
        fontFamily = poppinsFontFamily(), fontWeight = FontWeight.SemiBold, fontSize = 24.sp
    ), headlineMedium = TextStyle(
        fontFamily = poppinsFontFamily(), fontWeight = FontWeight.SemiBold, fontSize = 20.sp
    ), headlineSmall = TextStyle(
        fontFamily = poppinsFontFamily(), fontWeight = FontWeight.SemiBold, fontSize = 16.sp
    ), labelMedium = TextStyle(
        fontFamily = poppinsFontFamily(), fontWeight = FontWeight.Normal, fontSize = 14.sp
    ), labelSmall = TextStyle(
        fontFamily = poppinsFontFamily(), fontWeight = FontWeight.Normal, fontSize = 12.sp
    ), displayMedium = TextStyle(
        fontFamily = poppinsFontFamily(), fontWeight = FontWeight.SemiBold, fontSize = 16.sp
    )
)