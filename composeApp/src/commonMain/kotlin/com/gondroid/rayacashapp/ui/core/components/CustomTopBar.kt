package com.gondroid.rayacashapp.ui.core.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gondroid.rayacashapp.ui.core.BackgroundPrimaryColor
import com.gondroid.rayacashapp.ui.core.DefaultTextColor


@Composable
fun CustomTopBar(
    modifier: Modifier,
    title: String,
    navigateStart: (() -> Unit)? = null,
    navigateEnd: (() -> Unit)? = null
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(WindowInsets.statusBars.asPaddingValues())
            .background(BackgroundPrimaryColor),
        contentAlignment = Alignment.Center
    ) {
        if (navigateStart != null) {
            Box(
                modifier =
                    Modifier
                        .padding(8.dp)
                        .align(Alignment.CenterStart)
                        .clickable {
                            navigateStart()
                        },
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Add Task",
                    tint = DefaultTextColor,
                )

            }
        }


        Text(
            modifier = Modifier.align(Alignment.Center),
            text = title,
            color = DefaultTextColor,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            textAlign = TextAlign.Center
        )

        if (navigateEnd != null) {
            Box(
                modifier =
                    Modifier
                        .padding(8.dp)
                        .align(Alignment.CenterEnd)
                        .clickable {
                            navigateEnd()
                        },
            ) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "Add Task",
                    tint = DefaultTextColor,
                )

            }
        }

    }
}