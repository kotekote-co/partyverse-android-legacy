package co.kotekote.partyverse.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import co.kotekote.partyverse.screens.MainScreen
import co.kotekote.partyverse.screens.ProfileScreen

@Composable
fun SetupNavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(
            route = Screen.Home.route
        ) {
            MainScreen(navController = navController)
        }
        composable(
            route = Screen.Profile.route,
        )
        {
            ProfileScreen(navController = navController)
        }
    }
}
