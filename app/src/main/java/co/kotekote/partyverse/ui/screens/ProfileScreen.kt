package co.kotekote.partyverse.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
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
    Column(
        Modifier
            .statusBarsPadding()
            .padding(16.dp, 0.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 25.dp),
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

@Preview(name = "LightProfilePreview", group = "Light", showBackground = true, showSystemUi = true)
@Composable
fun ProfileScreenPreview() {
    ProfileScreen(PreviewNavActions())
}