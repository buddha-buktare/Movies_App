package me.buddha.moviesapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import me.buddha.moviesapp.data.model.local.MovieEntity
import me.buddha.moviesapp.data.model.local.RemoteKey

@Database(
    entities = [MovieEntity::class, RemoteKey::class],
    version = 1,
    exportSchema = false
)
abstract class MovieDatabase : RoomDatabase() {

    abstract fun moviesDao(): MoviesDao
    abstract fun remoteKeyDao() : RemoteKeyDao
}