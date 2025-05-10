package me.buddha.moviesapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavController

@Composable
fun NavHandler(
    navigator: Navigator,
    navController: NavController
) {
    LaunchedEffect(navigator.navigationActions) {
        navigator.navigationActions.collect { action ->
            when (action) {
                is NavigationAction.Navigate -> {
                    navController.navigate(action.destination, action.navOptions)
                }
                is NavigationAction.NavigateUp -> {
                    navController.navigateUp()
                }
            }
        }
    }
}