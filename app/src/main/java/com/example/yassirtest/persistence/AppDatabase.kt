package com.example.yassirtest.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.yassirtest.model.Movie
import com.example.yassirtest.model.MovieInfo

@Database(entities = [Movie::class, MovieInfo::class, RemoteKeys::class], version = 1, exportSchema = false)
@TypeConverters(value = [GenreIdsConverter::class, GenreListConverter::class])
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
    abstract fun remoteKeysDao(): RemoteKeysDao
}