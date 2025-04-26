package com.gondroid.rayacashapp.shared

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform