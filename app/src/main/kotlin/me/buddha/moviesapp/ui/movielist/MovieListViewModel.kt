package me.buddha.moviesapp.ui.movielist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.compose.LazyPagingItems
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import me.buddha.moviesapp.data.model.Movie
import me.buddha.moviesapp.domain.usecase.MovieUseCase
import me.buddha.moviesapp.navigation.Navigator
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val movieUseCase: MovieUseCase,
    private val navigator: Navigator
): ViewModel() {

    companion object {
        const val TAG = "MovieListViewModel"
    }

    lateinit var popularMovies: Flow<PagingData<Movie>>

    init {
        getMovies()
    }

    private fun getMovies() {
        popularMovies = movieUseCase.getPopularMoviesUseCase().cachedIn(viewModelScope)
    }
}

sealed class MovieUiState {
    object Loading : MovieUiState()
    data class Success(val movies: LazyPagingItems<Movie>) : MovieUiState()
    data class Error(val message: String) : MovieUiState()
}