package co.kotekote.partyverse.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import co.kotekote.partyverse.R
import co.kotekote.partyverse.ui.navigation.NavActions
import co.kotekote.partyverse.ui.navigation.PreviewNavActions

@Composable
fun ProfileScreen(navActions: NavActions) {
    Column(Modifier.statusBarsPadding().padding(16.dp, 0.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("WIP")
            IconButton(navActions.openSettings) {
                Icon(
                    Icons.Default.Settings,
                    contentDescription = stringResource(R.string.open_settings_button)
                )
            }
        }
    }
}

@Preview
@Composable
fun ProfileScreenPreview() {
    ProfileScreen(PreviewNavActions())
}