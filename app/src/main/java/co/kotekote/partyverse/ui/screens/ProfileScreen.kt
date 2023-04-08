package co.kotekote.partyverse.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessibleForward
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import co.kotekote.partyverse.ui.navigation.NavActions

@Composable
fun ProfileScreen(navActions: NavActions) {
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
                Icons.Default.AccessibleForward,
                contentDescription = "icon",
                modifier = Modifier
                    .clip(shape = RoundedCornerShape(15.dp))
                    .size(40.dp)
                    .border(1.dp, MaterialTheme.colors.secondary, CircleShape)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Column {
                Text(text = "keet", color = MaterialTheme.colors.secondaryVariant)
                Text(text = "мяу мяу я котик")
            }
        }
    }
}

@Preview
@Composable
fun ProfileCardPreview() {
    ProfileCard()
}