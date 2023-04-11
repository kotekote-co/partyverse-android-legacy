package co.kotekote.partyverse

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavHostController
import co.kotekote.partyverse.data.settings.subscribeToDarkTheme
import co.kotekote.partyverse.data.settings.themePreferenceState
import co.kotekote.partyverse.ui.navigation.PartyverseNavGraph
import co.kotekote.partyverse.ui.theme.PartyverseTheme
import com.google.accompanist.navigation.animation.rememberAnimatedNavController

class MainActivity : ComponentActivity() {

    lateinit var navController: NavHostController

    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            val preferredTheme by themePreferenceState(this)
            PartyverseTheme(darkTheme = preferredTheme.subscribeToDarkTheme()) {
                navController = rememberAnimatedNavController()
                PartyverseNavGraph(navController = navController)
            }
        }
    }
}
