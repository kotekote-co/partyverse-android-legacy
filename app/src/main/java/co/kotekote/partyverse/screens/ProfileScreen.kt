package co.kotekote.partyverse.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import co.kotekote.partyverse.R

@Composable
fun ProfileScreen(
    navController: NavController
) {
    Column(Modifier.padding(start = 15.dp, top = 70.dp, end = 15.dp)) {
        IconButton(onClick = {
            navController.popBackStack()
        }) {
            Image(painter = painterResource(id = R.drawable.baseline_arrow_back_24),
                contentDescription = "go back button")
        }
    }
}