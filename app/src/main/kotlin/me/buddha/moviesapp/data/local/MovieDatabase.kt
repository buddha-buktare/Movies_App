package me.buddha.moviesapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import me.buddha.moviesapp.data.model.local.MovieEntity
import javax.inject.Singleton

@Database(
    entities = [MovieEntity::class],
    version = 1
)
@Singleton
abstract class MovieDatabase : RoomDatabase() {

    abstract fun moviesDao(): MoviesDao
}