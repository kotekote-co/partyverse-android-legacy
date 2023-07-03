package co.kotekote.partyverse.ui.navigation

import androidx.compose.animation.*
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import co.kotekote.partyverse.ui.screens.homeNavigationRoute
import co.kotekote.partyverse.ui.screens.homeScreen
import co.kotekote.partyverse.ui.screens.loginScreen
import co.kotekote.partyverse.ui.screens.openLogin
import co.kotekote.partyverse.ui.screens.openProfile
import co.kotekote.partyverse.ui.screens.openSettings
import co.kotekote.partyverse.ui.screens.profileScreen
import co.kotekote.partyverse.ui.screens.settingsScreen
import com.google.accompanist.navigation.animation.AnimatedNavHost

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun PartyverseNavGraph(
    navController: NavHostController
) {
    AnimatedNavHost(
        navController = navController,
        startDestination = homeNavigationRoute
    ) {
        homeScreen(
            onSelfProfileClick = { navController.openProfile(0) }
        )
        profileScreen(
            onSettingsClick = navController::openSettings,
            onLoginClick = navController::openLogin
        )
        settingsScreen()
        loginScreen()
    }
}
