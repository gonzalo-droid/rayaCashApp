package com.gondroid.rayacashapp.shared

enum class ToastDurationType{
    SHORT,
    LONG
}

expect fun showToastMsg(msg:String,duration:ToastDurationType = ToastDurationType.SHORT)