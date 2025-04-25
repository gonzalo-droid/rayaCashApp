package com.gondroid.rayacashapp.ui.core.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gondroid.rayacashapp.ui.core.BackgroundPrimaryColor
import com.gondroid.rayacashapp.ui.core.DefaultTextColor
import com.gondroid.rayacashapp.ui.core.RayaColor
import org.jetbrains.compose.resources.painterResource
import rayacashapp.composeapp.generated.resources.Res
import rayacashapp.composeapp.generated.resources.ic_back
import rayacashapp.composeapp.generated.resources.ic_transactions


@Composable
fun CustomTopBar(
    modifier: Modifier,
    title: String,
    navigateStart: (() -> Unit)? = null,
    iconNavigateStart: Painter = painterResource(Res.drawable.ic_back),
    navigateEnd: (() -> Unit)? = null,
    iconNavigateEnd: Painter = painterResource(Res.drawable.ic_transactions),
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
                    modifier = Modifier.width(25.dp).height(25.dp),
                    contentDescription = "",
                    painter = iconNavigateStart,
                    tint = DefaultTextColor,
                )
            }
        }


        Text(
            modifier = Modifier.align(Alignment.Center),
            text = title,
            color = DefaultTextColor,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
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
                    modifier = Modifier.width(25.dp).height(25.dp),
                    contentDescription = "",
                    painter = iconNavigateEnd,
                    tint = DefaultTextColor,
                )

            }
        }

    }
}
