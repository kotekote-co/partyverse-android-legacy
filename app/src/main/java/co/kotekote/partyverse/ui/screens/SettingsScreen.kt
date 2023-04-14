package co.kotekote.partyverse.ui.screens

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import co.kotekote.partyverse.R
import co.kotekote.partyverse.data.settingsDataStore
import co.kotekote.partyverse.data.themePreferenceState
import co.kotekote.partyverse.ui.navigation.NavActions
import co.kotekote.partyverse.ui.navigation.PreviewNavActions
import kotlinx.coroutines.launch

@Composable
fun SettingsScreen(@Suppress("UNUSED_PARAMETER") navActions: NavActions) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val currentThemePreference by themePreferenceState(context)

    Column(
        modifier = Modifier
            .statusBarsPadding()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(stringResource(R.string.settings_title), style = MaterialTheme.typography.h4)
        Divider()
        EnumSettingsSection(
            title = stringResource(R.string.settings_theme_title),
            values = listOf(
                stringResource(R.string.settings_theme_system),
                stringResource(R.string.settings_theme_light),
                stringResource(R.string.settings_theme_dark),
            ),
            selected = currentThemePreference.number
        ) { newTheme ->
            coroutineScope.launch {
                context.settingsDataStore.updateData { current ->
                    current.toBuilder()
                        .setPreferredTheme(Settings.PreferredTheme.forNumber(newTheme))
                        .build()
                }
            }
        }
    }
}

@Composable
private fun SettingsSection(title: String, content: @Composable () -> Unit) {
    Text(title, style = MaterialTheme.typography.h6)
    content()
}

@Composable
private fun EnumSettingsSection(title: String, values: List<String>, selected: Int, onChange: (Int) -> Unit = {}) {
    SettingsSection(title) {
        Column(
            modifier = Modifier.selectableGroup()
        ) {
            values.forEachIndexed { i, caption ->
                val isSelected = i == selected
                Row(
                    modifier = Modifier
                        .selectable(
                            selected = isSelected,
                            onClick = {
                                onChange(i)
                            },
                            role = Role.RadioButton
                        )
                        .padding(16.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = isSelected,
                        onClick = null
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = caption,
                        style = MaterialTheme.typography.body2,
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SettingsScreenPreview() {
    SettingsScreen(PreviewNavActions())
}