package me.buddha.moviesapp.navigation

import com.google.gson.Gson
import me.buddha.moviesapp.data.model.Movie
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

sealed class Destination(val route: String) {

    data object MovieList: Destination("movie_list")

    data object MovieDetails: Destination("movie_details/{movieDetails}") {
        private val gson = Gson()
        operator fun invoke(movie: Movie): String {
            val movieJson = gson.toJson(movie)
            val encodedMovie = URLEncoder.encode(movieJson, StandardCharsets.UTF_8.toString())
            return "movie_details/$encodedMovie"
        }
    }

    data object Search: Destination("search")
}