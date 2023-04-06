package co.kotekote.partyverse

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import co.kotekote.partyverse.ui.navigation.SetupNavGraph
import co.kotekote.partyverse.ui.theme.PartyverseTheme

class MainActivity : ComponentActivity() {

    lateinit var navController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            PartyverseTheme(darkTheme = isSystemInDarkTheme()) {
                navController = rememberNavController()
                SetupNavGraph(navController = navController)
            }
        }
    }
}
