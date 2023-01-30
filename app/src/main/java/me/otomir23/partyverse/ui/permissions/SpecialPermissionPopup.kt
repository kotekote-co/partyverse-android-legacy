package me.otomir23.partyverse.ui.permissions

import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.vector.ImageVector
import com.google.accompanist.permissions.*

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun SpecialPermissionPopup(
    required: Boolean = false,
    permissionState: PermissionState,
    explanation: String,
    insistExplanation: String,
    icon: ImageVector
) {
    val dismissed: MutableState<Boolean> = remember {
        mutableStateOf(false)
    }

    PermissionPopup(
        icon = icon,
        explanation = if (permissionState.status.shouldShowRationale) insistExplanation else explanation,
        display = !permissionState.status.isGranted && (required || !dismissed.value || !permissionState.status.shouldShowRationale),
        onClick = {
            permissionState.launchPermissionRequest()
        }
    )
}