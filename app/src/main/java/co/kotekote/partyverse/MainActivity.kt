package co.kotekote.partyverse

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import co.kotekote.partyverse.data.subscribeToDarkTheme
import co.kotekote.partyverse.data.themePreferenceState
import co.kotekote.partyverse.ui.navigation.PartyverseNavGraph
import co.kotekote.partyverse.ui.theme.PartyverseTheme

class MainActivity : ComponentActivity() {

    lateinit var navController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            val preferredTheme by themePreferenceState(this)
            PartyverseTheme(darkTheme = preferredTheme.subscribeToDarkTheme()) {
                navController = rememberNavController()
                PartyverseNavGraph(navController = navController)
            }
        }
    }
}
