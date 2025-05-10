package me.buddha.moviesapp.data.remote

import me.buddha.moviesapp.data.model.response.MovieResponseDto
import me.buddha.moviesapp.domain.Constants
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("page") page: Int,
        @Query("api_key") apiKey: String = Constants.API_KEY,
    ): MovieResponseDto

    @GET("search/movie")
    suspend fun searchMovies(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("api_key") apiKey: String = Constants.API_KEY,
    ): MovieResponseDto
}