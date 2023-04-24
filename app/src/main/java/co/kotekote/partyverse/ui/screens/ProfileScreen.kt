package co.kotekote.partyverse.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Login
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.SignalWifiConnectedNoInternet4
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import co.kotekote.partyverse.R
import co.kotekote.partyverse.data.supabase.rememberSupabaseClient
import co.kotekote.partyverse.ui.navigation.NavActions
import co.kotekote.partyverse.ui.navigation.PreviewNavActions
import io.github.jan.supabase.gotrue.SessionStatus
import io.github.jan.supabase.gotrue.gotrue
import kotlinx.coroutines.launch

@Composable
fun ProfileScreen(navActions: NavActions) {
    val supabaseClient = rememberSupabaseClient()
    val scope = rememberCoroutineScope()
    val sessionStatus by supabaseClient.gotrue.sessionStatus.collectAsState()

    Column(
        Modifier
            .statusBarsPadding()
            .padding(16.dp, 0.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            val currentStatus = sessionStatus
            val logOut: () -> Unit = ({
                scope.launch {
                    supabaseClient.gotrue.invalidateSession()
                }
            })
            when (currentStatus) {
                is SessionStatus.Authenticated -> {
                    Text(
                        currentStatus.session.user?.email ?: "unknown",
                        style = MaterialTheme.typography.h5
                    )
                    IconButton(logOut) {
                        Icon(
                            Icons.Default.Logout,
                            contentDescription = stringResource(R.string.logout_button)
                        )
                    }
                }
                is SessionStatus.NetworkError -> {
                    Icon(Icons.Default.SignalWifiConnectedNoInternet4, stringResource(R.string.error_connection_short))
                    Text(stringResource(R.string.error_connection_short), style = MaterialTheme.typography.h5)
                }
                is SessionStatus.NotAuthenticated -> {
                    IconButton(navActions.openLogin) {
                        Icon(
                            Icons.Default.Login,
                            contentDescription = stringResource(R.string.login_button)
                        )
                    }
                }
                else -> {}
            }

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