package com.jogasoft.moviefinder.data.source.local.database

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import com.jogasoft.moviefinder.data.source.local.LocalMovie

@Database(
    version = 2,
    entities = [LocalMovie::class],
    autoMigrations = [
        AutoMigration(from = 1, to = 2)
    ]
)
abstract class MovieFinderDatabase : RoomDatabase() {
    abstract fun MovieDao(): MovieDao
}