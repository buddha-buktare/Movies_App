package me.buddha.moviesapp.data.datasource

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import me.buddha.moviesapp.data.local.MovieDatabase
import me.buddha.moviesapp.data.local.MovieRemoteMediator
import me.buddha.moviesapp.data.model.Movie
import me.buddha.moviesapp.data.paging.SearchMoviesPagingSource
import me.buddha.moviesapp.data.remote.MovieApi
import me.buddha.moviesapp.domain.datasource.MovieDataSource
import me.buddha.moviesapp.domain.mapper.toMovie
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class MovieDataSourceImpl @Inject constructor(
    private val movieDb: MovieDatabase,
    private val movieApi: MovieApi,
) : MovieDataSource {

    override fun getPopularMovies(): Flow<PagingData<Movie>> {

        return Pager(
            config = PagingConfig(pageSize = 20),
            remoteMediator = MovieRemoteMediator(
                movieApi = movieApi,
                movieDb = movieDb,
            ),
            pagingSourceFactory = { movieDb.moviesDao().getAllMovies() }
        ).flow.map { pagingData ->
            pagingData.map { it.toMovie() }
        }
    }

    override fun searchMovies(query: String): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = {
                SearchMoviesPagingSource(movieApi = movieApi, query = query)
            }
        ).flow
    }
}