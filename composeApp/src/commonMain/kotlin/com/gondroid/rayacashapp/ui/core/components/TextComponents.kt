package com.gondroid.rayacashapp.ui.core.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.gondroid.rayacashapp.ui.core.DefaultTextColor


@Composable
fun TextSmall(text: String) {
    Text(
        text = text, fontWeight = FontWeight.Normal, fontSize = 12.sp, color = DefaultTextColor,
    )
}

@Composable
fun TextMedium(text: String) {
    Text(
        text = text, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = DefaultTextColor,
    )
}