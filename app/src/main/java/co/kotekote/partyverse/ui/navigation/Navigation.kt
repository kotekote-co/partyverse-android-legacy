package co.kotekote.partyverse.ui.navigation

import androidx.navigation.NavController

object Routes {
    const val HOME = "home"
    const val PROFILE = "profile"
}

interface NavActions {
    val navigateHome: () -> Unit
    val openProfile: () -> Unit
}

class AppNavActions(navController: NavController) : NavActions {
    override val navigateHome = {
        navController.navigate(Routes.HOME)
    }

    override val openProfile = {
        navController.navigate(Routes.PROFILE)
    }
}

class PreviewNavActions : NavActions {
    override val navigateHome = {}
    override val openProfile = {}
}