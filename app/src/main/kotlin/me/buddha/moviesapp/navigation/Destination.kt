package me.buddha.moviesapp.navigation

sealed class Destination(val route: String) {

    object MovieList: Destination("movie_list")
    object MovieDetails: Destination("movie_details/{movieId}") {
        operator fun invoke(movieId: String) = "movie_details/$movieId"
    }
    object Search: Destination("search")

}