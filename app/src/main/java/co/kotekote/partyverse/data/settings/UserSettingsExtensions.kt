package co.kotekote.partyverse.data.settings

import Settings.PreferredTheme
import Settings.UserSettings
import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import kotlinx.coroutines.flow.map

val Context.settingsDataStore: DataStore<UserSettings> by dataStore(
    fileName = "settings.pb",
    serializer = UserSettingsSerializer
)

@Composable
fun PreferredTheme.subscribeToDarkTheme(): Boolean {
    return when (this) {
        PreferredTheme.ALWAYS_DARK -> true
        PreferredTheme.ALWAYS_LIGHT -> false
        else -> isSystemInDarkTheme()
    }
}

@SuppressLint("FlowOperatorInvokedInComposition")
@Composable
fun themePreferenceState(context: Context): State<PreferredTheme> {
    return context.settingsDataStore.data
        .map {
            it.preferredTheme
        }
        .collectAsState(initial = PreferredTheme.MATCH_SYSTEM)
}