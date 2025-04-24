package com.gondroid.rayacashapp

import androidx.compose.ui.window.ComposeUIViewController
import com.gondroid.rayacashapp.di.initKoin

fun MainViewController() = ComposeUIViewController(configure = { initKoin() }) { App() }