package com.gondroid.rayacashapp

import android.widget.Toast

actual fun showToastMsg(msg: String, duration: ToastDurationType) {

    val mContext = RayaCashApp.instance
    mContext?.let {
        when (duration) {
            ToastDurationType.SHORT -> Toast.makeText(it, msg, Toast.LENGTH_SHORT).show()
            ToastDurationType.LONG -> Toast.makeText(it, msg, Toast.LENGTH_LONG).show()
        }
    }
}