package co.kotekote.partyverse.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import co.kotekote.partyverse.ui.screens.MainScreen
import co.kotekote.partyverse.ui.screens.ProfileScreen

@Composable
fun PartyverseNavGraph(
    navController: NavHostController
) {
    val navActions = remember(navController) {
        NavActions(navController)
    }

    NavHost(
        navController = navController,
        startDestination = Routes.HOME
    ) {
        composable(
            route = Routes.HOME
        ) {
            MainScreen(navActions)
        }
        composable(
            route = Routes.PROFILE,
        )
        {
            ProfileScreen(navActions)
        }
    }
}
