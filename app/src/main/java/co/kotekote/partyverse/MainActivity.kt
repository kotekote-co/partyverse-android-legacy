package co.kotekote.partyverse

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import co.kotekote.partyverse.ui.navigation.SetupNavGraph
import co.kotekote.partyverse.ui.theme.PartyverseTheme

class MainActivity : ComponentActivity() {

    lateinit var navController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PartyverseTheme(darkTheme = true) {
                navController = rememberNavController()
                SetupNavGraph(navController = navController)
            }
        }
    }
}
