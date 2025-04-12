package me.buddha.moviesapp.ui.moviedetails

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import me.buddha.moviesapp.R
import me.buddha.moviesapp.data.model.Movie
import me.buddha.moviesapp.ui.theme.MoviesAppTheme

@Composable
fun MovieDetailsScreen(
    modifier: Modifier = Modifier,
    viewModel: MovieDetailsViewModel = hiltViewModel(),
) {
    MovieDetailsStateBased(
        movie = viewModel.movie,
        onBackClick = viewModel::onBackClick
    )
}

@Composable
fun MovieDetailsStateBased(
    movie: Movie? = null,
    onBackClick: () -> Unit,
) {
    movie?.run {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            AsyncImage(
                model = R.drawable.ic_back,
                contentDescription = "Back Icon",
                modifier = Modifier
                    .size(24.dp)
                    .clickable { onBackClick() }
            )
            AsyncImage(
                model = posterUrl(),
                contentDescription = title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .clip(RoundedCornerShape(12.dp))
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = title)
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = overview)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MovieDetailsPreview() {
    MoviesAppTheme {
        MovieDetailsStateBased(
            movie = Movie(1, "Title", "Overview", ""),
            onBackClick = {}
        )
    }
}