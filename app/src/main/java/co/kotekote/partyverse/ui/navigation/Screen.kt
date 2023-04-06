package co.kotekote.partyverse.ui.navigation

sealed class Screen(val route: String) {
    object Home : Screen(route = "home_screen")
    object Profile : Screen(route = "profile_screen")
}
