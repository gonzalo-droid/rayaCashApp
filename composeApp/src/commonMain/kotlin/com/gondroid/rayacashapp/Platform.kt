package com.gondroid.rayacashapp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform