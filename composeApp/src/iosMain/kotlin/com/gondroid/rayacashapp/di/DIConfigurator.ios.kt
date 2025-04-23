package com.gondroid.rayacashapp.di

import com.gondroid.rayacashapp.data.database.RayaCashDatabase
import com.gondroid.rayacashapp.data.database.getDatabase
import org.koin.core.module.Module
import org.koin.dsl.module

actual fun platformModule(): Module {
    return module {
        single<RayaCashDatabase> { getDatabase() }
    }
}