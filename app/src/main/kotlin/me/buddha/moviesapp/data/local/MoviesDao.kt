package me.buddha.moviesapp.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import me.buddha.moviesapp.data.model.local.MovieEntity

@Dao
interface MoviesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(list: List<MovieEntity>)

    @Query("SELECT * from movies")
    fun getAllMovies(): PagingSource<Int, MovieEntity>

    @Query("DELETE from movies")
    suspend fun clearAll()
}