package me.buddha.moviesapp.navigation

import androidx.navigation.NavOptionsBuilder
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import me.buddha.moviesapp.navigation.NavigationAction.Navigate
import me.buddha.moviesapp.navigation.NavigationAction.NavigateUp

interface Navigator {

    val startDestination: String
    val navigationActions: Flow<NavigationAction>

    suspend fun navigate(
        destination: String,
        navOptions: NavOptionsBuilder.() -> Unit = {}
    )

    suspend fun navigateUp()
}

class DefaultNavigator(
    override val startDestination: String
) : Navigator {
    private val _navigationActions = Channel<NavigationAction>()
    override val navigationActions = _navigationActions.receiveAsFlow()

    override suspend fun navigate(
        destination: String,
        navOptions: NavOptionsBuilder.() -> Unit
    ) {
        _navigationActions.send(
            Navigate(
                destination = destination,
                navOptions = navOptions,
            )
        )
    }

    override suspend fun navigateUp() {
        _navigationActions.send(NavigateUp)
    }
}