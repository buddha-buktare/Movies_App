package me.buddha.moviesapp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import me.buddha.moviesapp.data.remote.MovieApi
import me.buddha.moviesapp.domain.Constants
import me.buddha.moviesapp.navigation.DefaultNavigator
import me.buddha.moviesapp.navigation.Destination
import me.buddha.moviesapp.navigation.Navigator
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideNavigator(): Navigator = DefaultNavigator(startDestination = Destination.MovieList)

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
 }