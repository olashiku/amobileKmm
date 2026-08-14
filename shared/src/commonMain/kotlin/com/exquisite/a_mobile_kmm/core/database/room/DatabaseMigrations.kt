package com.exquisite.a_mobile_kmm.core.database.room

import androidx.room.migration.Migration
import androidx.sqlite.SQLiteConnection
import androidx.sqlite.execSQL

/**
 * Database migrations for AppDatabase
 *
 * IMPORTANT: When adding new entities or modifying schema:
 * 1. Increment the version number in AppDatabase
 * 2. Add a new migration here (e.g., MIGRATION_1_2)
 * 3. Add the migration to the migrations list
 * 4. Test the migration thoroughly before deploying
 *
 * NEVER use fallbackToDestructiveMigration(true) in production!
 */

/**
 * Example migration from version 1 to 2
 * Uncomment and modify when you need to migrate to version 2
 */
/*
val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(connection: SQLiteConnection) {
        // Example: Add a new column
        connection.execSQL("ALTER TABLE cart_table ADD COLUMN new_column TEXT")

        // Example: Create a new table
        connection.execSQL(
            """
            CREATE TABLE IF NOT EXISTS new_table (
                id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                name TEXT NOT NULL
            )
            """.trimIndent()
        )
    }
}
*/

/**
 * List of all available migrations
 * Add new migrations here as you create them
 */
val ALL_MIGRATIONS = arrayOf<Migration>(
    // Add migrations here as you create them
    // Example: MIGRATION_1_2, MIGRATION_2_3, etc.
)
