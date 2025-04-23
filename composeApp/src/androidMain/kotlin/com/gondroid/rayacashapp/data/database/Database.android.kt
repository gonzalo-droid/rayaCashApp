package com.gondroid.rayacashapp.data.database

import android.content.Context
import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import kotlinx.coroutines.Dispatchers
import java.util.UUID

fun getDatabase(context: Context): RayaCashDatabase {
    val dbFile = context.getDatabasePath(DATABASE_NAME)
    return Room.databaseBuilder<RayaCashDatabase>(context, dbFile.absolutePath)
        .setDriver(BundledSQLiteDriver())
        .setQueryCoroutineContext(Dispatchers.IO).addMigrations()
        .build()
}

// androidMain
actual fun generateUUID(): String = UUID.randomUUID().toString()
