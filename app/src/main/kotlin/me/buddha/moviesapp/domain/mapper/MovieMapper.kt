package me.buddha.moviesapp.domain.mapper

import me.buddha.moviesapp.data.model.Movie
import me.buddha.moviesapp.data.model.local.MovieEntity
import me.buddha.moviesapp.data.model.response.MovieDto

fun MovieDto.toMovie(): Movie {
    return Movie(
        id = id,
        title = title,
        overview = overview,
        posterPath = posterPath,
    )
}

fun MovieDto.toMovieEntity(): MovieEntity {
    return MovieEntity(
        id = id,
        title = title,
        overview = overview,
        posterPath = posterPath
    )
}

fun MovieEntity.toMovie() = Movie(
    id = id,
    title = title,
    overview = overview,
    posterPath = posterPath
)

fun Movie.toMovieEntity() = MovieEntity(
    id = id,
    title = title,
    overview = overview,
    posterPath = posterPath
)