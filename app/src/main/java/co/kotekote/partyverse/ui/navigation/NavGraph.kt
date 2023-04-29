package co.kotekote.partyverse.ui.navigation

import androidx.compose.animation.*
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import co.kotekote.partyverse.ui.screens.LoginScreen
import co.kotekote.partyverse.ui.screens.MainScreen
import co.kotekote.partyverse.ui.screens.ProfileScreen
import co.kotekote.partyverse.ui.screens.SettingsScreen
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun PartyverseNavGraph(
    navController: NavHostController
) {
    val navActions = remember(navController) {
        AppNavActions(navController)
    }

    AnimatedNavHost(
        navController = navController,
        startDestination = Routes.HOME
    ) {
        composable(
            route = Routes.HOME,
            exitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { -it },
                    animationSpec = tween(
                        durationMillis = 300,
                        easing = FastOutSlowInEasing
                    )
                ) +
                        fadeOut(animationSpec = tween(300))
            },
            enterTransition = {
                slideInHorizontally(
                    initialOffsetX = { -it },
                    animationSpec = tween(
                        durationMillis = 300,
                        easing = FastOutSlowInEasing
                    )
                ) + fadeIn(animationSpec = tween(300))
            }
        ) {
            MainScreen(navActions)
        }
        composable(
            route = Routes.PROFILE
        ) {
            ProfileScreen(navActions)
        }
        composable(
            route = Routes.SETTINGS
        ) {
            SettingsScreen(navActions)
        }
        composable(
            route = Routes.LOGIN
        ) {
            LoginScreen(navActions)
        }
    }
}
