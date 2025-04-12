package me.buddha.moviesapp.data.local

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.LoadType.APPEND
import androidx.paging.LoadType.PREPEND
import androidx.paging.LoadType.REFRESH
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import me.buddha.moviesapp.data.model.local.MovieEntity
import me.buddha.moviesapp.data.remote.MovieApi
import me.buddha.moviesapp.domain.mapper.toMovieEntity
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class MovieRemoteMediator @Inject constructor(
    private val movieDb: MovieDatabase,
    private val movieApi: MovieApi,
): RemoteMediator<Int, MovieEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MovieEntity>
    ): MediatorResult {
        return try {
            val page = when(loadType) {
                REFRESH -> 1
                PREPEND -> {
                    return MediatorResult.Success(endOfPaginationReached = true)
                }
                APPEND -> {
                    val lastItem = state.lastItemOrNull()
                    val lastPage = (state.pages.size)
                    lastPage + 1
                }
            }

            val response = movieApi.getPopularMovies(page = page)
            val movies = response.results.map { it.toMovieEntity() }

            movieDb.withTransaction {
                if (loadType == REFRESH) {
                    movieDb.moviesDao().clearAll()
                }
                movieDb.moviesDao().insertAll(movies)
            }

            MediatorResult.Success(endOfPaginationReached = page >= response.totalPages)

        } catch (e: HttpException) {
            MediatorResult.Error(e)
        } catch (e: IOException) {
            MediatorResult.Error(e)
        }
    }
}