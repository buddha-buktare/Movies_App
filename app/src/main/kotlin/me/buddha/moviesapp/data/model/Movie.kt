package me.buddha.moviesapp.data.model

import me.buddha.moviesapp.domain.Constants

data class Movie(
    val id: Int,
    val title: String,
    val overview: String,
    val posterPath: String?
) {
    fun posterUrl(): String = Constants.BASE_IMAGE_URL + posterPath
}