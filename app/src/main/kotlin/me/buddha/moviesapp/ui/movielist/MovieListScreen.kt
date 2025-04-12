package me.buddha.moviesapp.ui.movielist

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems

@Composable
fun MovieListScreen(
    modifier: Modifier = Modifier,
    viewModel: MovieListViewModel = hiltViewModel(),
) {
    val movies = viewModel.popularMovies.collectAsLazyPagingItems()

    LazyColumn {
        items(movies.itemCount) { index ->
            val movie = movies[index]
            if (movie != null) {
                Text(text = movie.toString())
            }
        }

        // Optional: Handle loading states
        when (movies.loadState.append) {
            is LoadState.Loading -> {
                item { CircularProgressIndicator(modifier = Modifier.padding(16.dp)) }
            }
            is LoadState.Error -> {
                item { Text("Error loading more movies") }
            }
            else -> Unit
        }
    }


}