package co.kotekote.partyverse.ui.navigation

import androidx.navigation.NavController

object Routes {
    const val HOME = "home"
    const val PROFILE = "profile"
}

class NavActions(navController: NavController) {
    val navigateHome: () -> Unit = {
        navController.navigate(Routes.HOME)
    }

    val openProfile: () -> Unit = {
        navController.navigate(Routes.PROFILE)
    }
}