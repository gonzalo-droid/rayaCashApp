package com.gondroid.rayacashapp


import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.gondroid.rayacashapp.ui.core.navigation.NavigationRoot
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme {
        NavigationRoot()
    }
}