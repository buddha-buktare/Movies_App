package me.buddha.moviesapp.ui.moviedetails

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import me.buddha.moviesapp.navigation.Navigator
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val navigator: Navigator
): ViewModel() {

    companion object {
        const val TAG = "MovieDetailsViewModel"
    }
}