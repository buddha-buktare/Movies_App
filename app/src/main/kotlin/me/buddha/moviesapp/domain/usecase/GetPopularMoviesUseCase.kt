package me.buddha.moviesapp.domain.usecase

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import me.buddha.moviesapp.data.model.Movie
import me.buddha.moviesapp.domain.datasource.MovieDataSource
import javax.inject.Inject

class GetPopularMoviesUseCase @Inject constructor(
    private val dataSource: MovieDataSource
) {
    operator fun invoke(): Flow<PagingData<Movie>> {
        return dataSource.getPopularMovies()
    }
}