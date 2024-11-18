package com.cenkeraydin.words.util

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun GradientText(text:String) {
    Text(
        text = text,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
        style = TextStyle(
            brush = Brush.linearGradient(
                colors = if (isSystemInDarkTheme()) {
                    listOf(Color(0xFFFF4081), Color(0xFF448AFF))
                } else {
                    listOf(Color.Red, Color.Blue)
                }
            ),
            shadow = if (isSystemInDarkTheme()) {
                Shadow(color = Color.White, offset = Offset(2f, 2f), blurRadius = 4f)
            } else {
                null
            }
        )
    )
}
