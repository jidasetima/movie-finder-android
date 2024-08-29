package com.jogasoft.moviefinder.data.source.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jogasoft.moviefinder.data.source.local.LocalMovie

@Database(entities = [LocalMovie::class], version = 1)
abstract class MovieFinderDatabase : RoomDatabase() {
    abstract fun MovieDao(): MovieDao
}