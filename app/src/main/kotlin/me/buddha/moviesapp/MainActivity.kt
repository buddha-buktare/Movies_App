package me.buddha.moviesapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import me.buddha.moviesapp.data.model.Movie
import me.buddha.moviesapp.navigation.Destination
import me.buddha.moviesapp.navigation.NavHandler
import me.buddha.moviesapp.navigation.Navigator
import me.buddha.moviesapp.ui.moviedetails.MovieDetailsScreen
import me.buddha.moviesapp.ui.moviedetails.MovieDetailsViewModel
import me.buddha.moviesapp.ui.movielist.MovieListScreen
import me.buddha.moviesapp.ui.movielist.MovieListViewModel
import me.buddha.moviesapp.ui.search.SearchScreen
import me.buddha.moviesapp.ui.search.SearchViewModel
import me.buddha.moviesapp.ui.theme.MoviesAppTheme
import java.net.URLDecoder
import java.nio.charset.StandardCharsets
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var navigator: Navigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MoviesAppTheme {
                val navController = rememberNavController()
                NavHandler(
                    navigator = navigator,
                    navController = navController
                )
                Box(modifier = Modifier.padding(vertical = 20.dp)) {
                    NavHost(
                        navController = navController,
                        startDestination = Destination.MovieList.route
                    ) {
                        composable(Destination.MovieList.route) {
                            val movieListViewModel: MovieListViewModel = hiltViewModel()
                            MovieListScreen(
                                viewModel = movieListViewModel
                            )
                        }

                        composable(Destination.MovieDetails.route) { navBackStackEntry ->
                            val movieDetailsViewModel: MovieDetailsViewModel = hiltViewModel()
                            val movieJson = navBackStackEntry.arguments?.getString("movieDetails")
                            val movie = movieJson?.let {
                                val decoded = URLDecoder.decode(it, StandardCharsets.UTF_8.toString())
                                Gson().fromJson(decoded, Movie::class.java)
                            }
                            movieDetailsViewModel.movie = movie

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
}