package co.kotekote.partyverse.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import co.kotekote.partyverse.R

@Composable
fun ProfileScreen(navController: NavController) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 15.dp, top = 75.dp)
            .background(MaterialTheme.colors.background)
    ) {
        ProfileCard()
    }
}

@Composable
fun ProfileCard() {
    Card {
        Row(
            modifier = Modifier
                .padding(all = 8.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.baseline_accessible_forward_24),
                contentDescription = "icon",
                modifier = Modifier
                    .clip(shape = RoundedCornerShape(15.dp))
                    .size(40.dp)
                    .border(1.dp, MaterialTheme.colors.secondary, CircleShape)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Column {
                Text(text = "keet", color = MaterialTheme.colors.secondaryVariant)
                Text(text = "кстати, я хуесос")
            }
        }
    }
}

@Preview
@Composable
fun ProfileCardPreview() {
    ProfileCard()
}