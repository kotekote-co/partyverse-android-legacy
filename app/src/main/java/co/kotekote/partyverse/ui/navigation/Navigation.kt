package co.kotekote.partyverse.ui.navigation

import androidx.navigation.NavController

object Routes {
    const val HOME = "home"
    const val PROFILE = "profile"
    const val SETTINGS = "settings"
}

interface NavActions {
    val navigateHome: () -> Unit
    val openProfile: () -> Unit
    val openSettings: () -> Unit
}

class AppNavActions(navController: NavController) : NavActions {
    override val navigateHome = {
        navController.navigate(Routes.HOME)
    }

    override val openProfile = {
        navController.navigate(Routes.PROFILE)
    }

    override val openSettings = {
        navController.navigate(Routes.SETTINGS)
    }
}

class PreviewNavActions : NavActions {
    override val navigateHome = {}
    override val openProfile = {}
    override val openSettings = {}
}