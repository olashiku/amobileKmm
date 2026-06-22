package com.exquisite.a_mobile_kmm.core.database

import androidx.room.Room
import androidx.room.RoomDatabase
import com.exquisite.a_mobile_kmm.core.database.room.AppDatabase
import platform.Foundation.NSHomeDirectory

fun getDatabaseBuilder(): RoomDatabase.Builder<AppDatabase> {
    val dbFilePath = NSHomeDirectory() + "/amobile.db"
    return Room.databaseBuilder<AppDatabase>(
        name = dbFilePath
    )
}