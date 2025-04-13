package me.buddha.moviesapp.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import me.buddha.moviesapp.data.model.Movie
import me.buddha.moviesapp.data.remote.MovieApi
import me.buddha.moviesapp.domain.mapper.toMovie
import javax.inject.Inject

class SearchMoviesPagingSource @Inject constructor(
    private val movieApi: MovieApi,
    private val query: String
) : PagingSource<Int, Movie>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return try {
            val page = params.key ?: 1
            val response = movieApi.searchMovies(
                query = query,
                page = page
            )
            val movies = response.results.map { it.toMovie() }

            LoadResult.Page(
                data = movies,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (page < response.totalPages) page + 1 else null
            )

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? = null
}
