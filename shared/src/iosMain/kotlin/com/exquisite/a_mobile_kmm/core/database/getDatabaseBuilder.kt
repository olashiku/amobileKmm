package com.exquisite.a_mobile_kmm.core.database

import androidx.room.Room
import androidx.room.RoomDatabase
import com.exquisite.a_mobile_kmm.core.database.room.AppDatabase
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSURL
import platform.Foundation.NSUserDomainMask

@OptIn(ExperimentalForeignApi::class)
fun getDatabaseBuilder(): RoomDatabase.Builder<AppDatabase> {
    val documentDirectory: NSURL? = NSFileManager.defaultManager.URLForDirectory(
        directory = NSDocumentDirectory,
        inDomain = NSUserDomainMask,
        appropriateForURL = null,
        create = false,
        error = null
    )
    val dbFilePath = requireNotNull(documentDirectory).path + "/amobile.db"
    return Room.databaseBuilder<AppDatabase>(
        name = dbFilePath
    )
}