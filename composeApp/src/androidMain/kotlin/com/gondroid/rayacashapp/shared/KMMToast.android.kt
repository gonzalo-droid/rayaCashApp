package com.gondroid.rayacashapp.shared

import android.widget.Toast
import com.gondroid.rayacashapp.ApplicationCashApp

actual fun showToastMsg(msg: String, duration: ToastDurationType) {

    val mContext = ApplicationCashApp.Companion.instance
    mContext?.let {
        when (duration) {
            ToastDurationType.SHORT -> Toast.makeText(it, msg, Toast.LENGTH_SHORT).show()
            ToastDurationType.LONG -> Toast.makeText(it, msg, Toast.LENGTH_LONG).show()
        }
    }
}