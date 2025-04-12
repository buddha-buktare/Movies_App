package me.buddha.moviesapp.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import me.buddha.moviesapp.data.datasource.MovieDataSourceImpl
import me.buddha.moviesapp.domain.datasource.MovieDataSource
import me.buddha.moviesapp.domain.usecase.GetPopularMoviesUseCase
import me.buddha.moviesapp.domain.usecase.MovieUseCase
import me.buddha.moviesapp.domain.usecase.SearchMoviesUseCase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class NetworkModule {

    companion object {
        @Provides
        @Singleton
        fun provideMovieUseCase(
            dataSource: MovieDataSource
        ) = MovieUseCase(
            getPopularMoviesUseCase = GetPopularMoviesUseCase(dataSource),
            searchMoviesUseCase = SearchMoviesUseCase(dataSource)
        )
    }


    @Binds
    abstract fun bindDataSourceImpl(
        dataSourceImpl: MovieDataSourceImpl
    ): MovieDataSource
}