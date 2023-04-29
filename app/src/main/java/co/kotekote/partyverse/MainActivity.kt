package co.kotekote.partyverse

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import co.kotekote.partyverse.data.settings.subscribeToDarkTheme
import co.kotekote.partyverse.data.settings.themePreferenceState
import co.kotekote.partyverse.data.supabase.SupabaseSingleton
import co.kotekote.partyverse.ui.navigation.PartyverseNavGraph
import co.kotekote.partyverse.ui.theme.PartyverseTheme
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import io.github.jan.supabase.gotrue.handleDeeplinks

class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        SupabaseSingleton.getClient(this).handleDeeplinks(intent)
        setContent {
            val preferredTheme by themePreferenceState(this)
            val isDarkTheme = preferredTheme.subscribeToDarkTheme()
            PartyverseTheme(darkTheme = isDarkTheme) {
                val navController = rememberAnimatedNavController()
                PartyverseNavGraph(navController = navController)
                val systemUiController = rememberSystemUiController()
                SideEffect {
                    systemUiController.setSystemBarsColor(Color.Transparent, !isDarkTheme)
                }
            }
        }
        extendScreen()
    }

    private fun extendScreen() {
        WindowCompat.setDecorFitsSystemWindows(window, false)
    }
}
