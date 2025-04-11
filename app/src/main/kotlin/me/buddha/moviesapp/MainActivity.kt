package me.buddha.moviesapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import me.buddha.moviesapp.navigation.Destination
import me.buddha.moviesapp.ui.moviedetails.MovieDetailsScreen
import me.buddha.moviesapp.ui.moviedetails.MovieDetailsViewModel
import me.buddha.moviesapp.ui.movielist.MovieListScreen
import me.buddha.moviesapp.ui.movielist.MovieListViewModel
import me.buddha.moviesapp.ui.search.SearchScreen
import me.buddha.moviesapp.ui.search.SearchViewModel
import me.buddha.moviesapp.ui.theme.MoviesAppTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MoviesAppTheme {
                NavHost(
                    navController = rememberNavController(),
                    startDestination = Destination.MovieList.route
                ) {
                    composable(Destination.MovieList.route) {
                        val movieListViewModel: MovieListViewModel = hiltViewModel()
                        MovieListScreen(
                            viewModel = movieListViewModel
                        )
                    }

                    composable(Destination.MovieDetails.route) {
                        val movieDetailsViewModel: MovieDetailsViewModel = hiltViewModel()
                        MovieDetailsScreen(
                            viewModel = movieDetailsViewModel
                        )
                    }

                    composable(Destination.Search.route) {
                        val searchViewModel: SearchViewModel = hiltViewModel()
                        SearchScreen(
                            viewModel = searchViewModel
                        )
                    }
                }
            }
        }
    }
}