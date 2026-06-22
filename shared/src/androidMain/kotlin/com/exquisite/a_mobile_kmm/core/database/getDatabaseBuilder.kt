package com.exquisite.a_mobile_kmm.core.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.exquisite.a_mobile_kmm.core.database.room.AppDatabase

fun getDatabaseBuilder(context: Context): RoomDatabase.Builder<AppDatabase> {
    val appContext = context.applicationContext
    val dbFile = appContext.getDatabasePath("amobile.db")
    return Room.databaseBuilder<AppDatabase>(
        context = appContext,
        name = dbFile.absolutePath
    )
}