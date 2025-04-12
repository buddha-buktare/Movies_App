package me.buddha.moviesapp.ui.moviedetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import me.buddha.moviesapp.data.model.Movie
import me.buddha.moviesapp.navigation.Navigator
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val navigator: Navigator
): ViewModel() {

    companion object {
        const val TAG = "MovieDetailsViewModel"
    }
    var movie: Movie? = null

    fun onBackClick() {
        viewModelScope.launch {
            navigator.navigateUp()
        }
    }
}