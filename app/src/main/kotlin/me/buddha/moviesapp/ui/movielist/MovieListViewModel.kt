package me.buddha.moviesapp.ui.movielist

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import me.buddha.moviesapp.navigation.Navigator
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val navigator: Navigator
): ViewModel() {

    companion object {
        const val TAG = "MovieListViewModel"
    }
}