package com.gondroid.rayacashapp

import android.app.Application
import com.gondroid.rayacashapp.di.initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger

class RayaCashApp : Application() {

    companion object{
        var instance: RayaCashApp? = null
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        initKoin {
            androidLogger() // logging in DI Koin
            androidContext(this@RayaCashApp) // inject context
        }

    }

}