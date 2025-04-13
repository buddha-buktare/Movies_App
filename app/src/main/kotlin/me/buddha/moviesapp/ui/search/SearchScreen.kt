package me.buddha.moviesapp.ui.search

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import me.buddha.moviesapp.R
import me.buddha.moviesapp.data.model.Movie
import me.buddha.moviesapp.ui.common.ErrorScreen
import me.buddha.moviesapp.ui.movielist.fakeMoviePagingItems
import me.buddha.moviesapp.ui.theme.MoviesAppTheme

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel = hiltViewModel()
) {
    val searchResults = viewModel.movies.collectAsLazyPagingItems()
    val query by viewModel.searchQuery.collectAsStateWithLifecycle()

    SearchScreenState(
        searchResults = searchResults,
        query = query,
        onSearchQueryChange = viewModel::onSearchQueryChange,
        clearQuery = viewModel::clearSearch,
        navigateToMovieDetails = viewModel::navigateToDetails
    )
}

@Composable
fun SearchScreenState(
    searchResults: LazyPagingItems<Movie>,
    query: String,
    onSearchQueryChange: (String) -> Unit,
    clearQuery: () -> Unit,
    navigateToMovieDetails: (Movie) -> Unit,
) {
    val isEmptyQuery = query.isBlank()
    val isEmptyResult = searchResults.itemCount == 0 && searchResults.loadState.refresh is LoadState.NotLoading
    val isError = searchResults.loadState.refresh is LoadState.Error

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        SearchInputField(
            value = query,
            onValueChange = onSearchQueryChange,
            clearQuery = clearQuery,
        )
        Spacer(modifier = Modifier.height(12.dp))
        Box(
            modifier = Modifier.fillMaxSize(),
        ) {
            when {
                isEmptyQuery -> {
                    // User hasn't typed anything yet
                    Text(
                        text = "Start typing to search movies...",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }

                isError -> {
                    // Show error + retry
                    ErrorScreen(
                        message = "",
                        onRetry = { onSearchQueryChange(query) }
                    )
                }

                isEmptyResult -> {
                    // No movies matched the query
                    Text(
                        text = "No results found.",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }

                else -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        items(searchResults.itemCount) { index ->
                            val movie = searchResults[index]
                            movie?.let { currentMovie ->
                                MovieItem(
                                    movie = currentMovie,
                                    onClick = { navigateToMovieDetails(currentMovie) }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun MovieItem(
    movie: Movie,
    onClick: () -> Unit
) {
   Row(
       modifier = Modifier.fillMaxWidth().clickable { onClick() },
       // verticalAlignment = ,
   ) {
       AsyncImage(
           model = ImageRequest.Builder(LocalContext.current)
               .data(movie.posterUrl())
               .crossfade(true)
               .build(),
           contentDescription = movie.title,
           modifier = Modifier.size(60.dp),
           contentScale = ContentScale.Crop,
           placeholder = painterResource(R.drawable.movie_placeholder)
       )
       Spacer(modifier = Modifier.width(10.dp))
       Text(
           text = movie.title
       )
   }
}

@Composable
private fun SearchInputField(
    value: String,
    onValueChange: (String) -> Unit,
    clearQuery: () -> Unit,
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        leadingIcon = {
            AsyncImage(
                model = R.drawable.ic_search,
                contentDescription = "Search Icon",
                modifier = Modifier.size(24.dp),
                colorFilter = ColorFilter.tint(Color.Gray)
            )
        },
        trailingIcon = {
            if(value.isNotEmpty()) {
                AsyncImage(
                    model = R.drawable.ic_close,
                    contentDescription = "Search Icon",
                    modifier = Modifier.size(24.dp).clickable { clearQuery() },
                    colorFilter = ColorFilter.tint(Color.Gray)
                )
            }
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
    )
}

@Preview
@Composable
private fun SearchScreenStatePreview() {
    MoviesAppTheme {
        SearchScreenState(
            fakeMoviePagingItems(),
            "sada",
            {},
            {},
            {}
        )
    }
}