package me.buddha.moviesapp.domain.usecase

data class MovieUseCase(
    val getPopularMoviesUseCase: GetPopularMoviesUseCase,
    val searchMoviesUseCase: SearchMoviesUseCase
)
