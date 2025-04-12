package me.buddha.moviesapp.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import me.buddha.moviesapp.data.datasource.MovieDataSourceImpl
import me.buddha.moviesapp.data.remote.MovieApi
import me.buddha.moviesapp.domain.Constants
import me.buddha.moviesapp.domain.datasource.MovieDataSource
import me.buddha.moviesapp.domain.usecase.GetPopularMoviesUseCase
import me.buddha.moviesapp.domain.usecase.MovieUseCase
import me.buddha.moviesapp.domain.usecase.SearchMoviesUseCase
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class NetworkModule {

    companion object {

        @Provides
        @Singleton
        fun provideOkHttpClient(): OkHttpClient {
            val loggingInterceptor = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }

            return OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build()
        }

        @Provides
        @Singleton
        fun provideRetrofit() = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        @Provides
        @Singleton
        fun provideApiService(
            retrofit: Retrofit
        ) = retrofit.create(MovieApi::class.java)

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