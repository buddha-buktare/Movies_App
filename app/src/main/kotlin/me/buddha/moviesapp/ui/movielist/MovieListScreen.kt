package me.buddha.moviesapp.ui.movielist

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import kotlinx.coroutines.flow.MutableStateFlow
import me.buddha.moviesapp.R
import me.buddha.moviesapp.data.model.Movie
import me.buddha.moviesapp.ui.common.ErrorScreen
import me.buddha.moviesapp.ui.common.ShimmerLoadingBox
import me.buddha.moviesapp.ui.theme.MoviesAppTheme

@Composable
fun MovieListScreen(
    modifier: Modifier = Modifier,
    viewModel: MovieListViewModel = hiltViewModel(),
) {
    val pagingData = viewModel.popularMovies.collectAsLazyPagingItems()

    val hasData = pagingData.itemCount > 0
    val isLoading = pagingData.loadState.refresh is LoadState.Loading
    val isError = pagingData.loadState.refresh is LoadState.Error

    val uiState: MovieUiState = when {
        isLoading && !hasData -> MovieUiState.Loading
        isError && !hasData -> {
            val error = (pagingData.loadState.refresh as LoadState.Error).error
            MovieUiState.Error(error.message ?: "Unknown error")
        }
        else -> MovieUiState.Success(pagingData)
    }

    when (uiState) {
        is MovieUiState.Loading -> {
            MovieListShimmer() // Your shimmer component
        }

        is MovieUiState.Error -> {
            ErrorScreen(
                message = uiState.message,
                onRetry = { pagingData.retry() }
            )
        }

        is MovieUiState.Success -> {
            MovieList(
                movies = uiState.movies,
                navigateToSearch = { viewModel.navigateToSearch() },
                navigateToDetails = { movie -> viewModel.navigateToDetails(movie) }
            )
        }
    }
}

@Composable
fun MovieList(
    movies: LazyPagingItems<Movie>,
    navigateToSearch: () -> Unit,
    navigateToDetails: (Movie) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SearchBox(
            onClick = navigateToSearch
        )
        Spacer(modifier = Modifier.height(10.dp))
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            items(movies.itemCount) { index ->
                val movie = movies[index]
                movie?.let {
                    MovieItem(
                        movie = movie,
                        onClick = { navigateToDetails(movie) }
                    )
                }
            }
            when (movies.loadState.append) {
                is LoadState.Loading -> {
                    item {
                        CircularProgressIndicator(
                            modifier = Modifier.fillMaxWidth().padding(16.dp)
                        )
                    }
                }

                is LoadState.Error -> {
                    item {
                        Text(
                            text = "Error loading more movies...",
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }

                else -> Unit
            }
        }
    }
}

@Composable
private fun MovieItem(
    movie: Movie,
    onClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(movie.posterUrl())
                .crossfade(true)
                .build(),
            contentDescription = movie.title,
            modifier = Modifier.size(160.dp),
            contentScale = ContentScale.Crop,
            placeholder = painterResource(R.drawable.movie_placeholder)
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = movie.title
        )
    }
}

@Composable
fun MovieListShimmer() {
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        ShimmerLoadingBox(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
        )
        Spacer(modifier = Modifier.height(10.dp))
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            items(10) { _ ->
                ShimmerLoadingBox(
                    modifier = Modifier.size(160.dp)
                )
            }
        }
    }
}

@Composable
private fun SearchBox(
    onClick: () -> Unit,
) {
    OutlinedTextField(
        value = "",
        onValueChange = {},
        enabled = false,
        leadingIcon = {
            AsyncImage(
                model = R.drawable.ic_search,
                contentDescription = "Search Icon",
                modifier = Modifier.size(24.dp),
                colorFilter = ColorFilter.tint(Color.Gray)
            )
        },
        placeholder = {
            Text(text = "Search movies")
        },
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .border(
                width = 1.dp,
                color = Color.Gray,
                shape = RoundedCornerShape(10.dp)
            )
            .clickable { onClick() }
    )
}

@Composable
fun fakeMoviePagingItems(): LazyPagingItems<Movie> {
    val movies = List(10) {
        Movie(
            id = it,
            title = "Movie $it",
            overview = "This is the overview of Movie $it",
            posterPath = "/path_$it.jpg",
        )
    }

    val fakePagingData = PagingData.from(movies)
    val pagerFlow = MutableStateFlow(fakePagingData)

    return pagerFlow.collectAsLazyPagingItems()
}

@Preview(showBackground = true)
@Composable
private fun MovieListPreview() {
    MoviesAppTheme {
        MovieList(
            movies = fakeMoviePagingItems(),
            navigateToSearch = {},
            navigateToDetails = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun MovieListShimmerPreview() {
    MoviesAppTheme {
        MovieListShimmer()
    }
}

