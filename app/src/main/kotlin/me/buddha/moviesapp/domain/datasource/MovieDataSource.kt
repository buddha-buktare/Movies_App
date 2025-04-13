
package me.buddha.moviesapp.domain.datasource

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import me.buddha.moviesapp.data.model.Movie

interface MovieDataSource {

    fun getPopularMovies(): Flow<PagingData<Movie>>

    fun searchMovies(query: String): Flow<PagingData<Movie>>
}