package me.buddha.moviesapp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import me.buddha.moviesapp.navigation.DefaultNavigator
import me.buddha.moviesapp.navigation.Destination
import me.buddha.moviesapp.navigation.Navigator
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideNavigator(): Navigator = DefaultNavigator(startDestination = Destination.MovieList.route)
 }