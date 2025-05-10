package me.buddha.moviesapp.data.model.response

import com.google.gson.annotations.SerializedName

data class MovieResponseDto(
    val results: List<MovieDto>,
    val page: Int,
    @SerializedName("total_pages")
    val totalPages: Int
)
