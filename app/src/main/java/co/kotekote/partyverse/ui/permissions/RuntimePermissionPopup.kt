package co.kotekote.partyverse.ui.permissions

import android.app.Activity
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import com.google.accompanist.permissions.*

enum class RuntimePermissionState {
    GRANTED,
    NEVER_ASKED,
    DENIED,
    DENIED_FOREVER
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun RuntimePermissionPopup(
    required: Boolean = false,
    permissionState: PermissionState,
    explanation: String,
    insistExplanation: String,
    foreverDeniedExplanation: String,
    icon: ImageVector
) {
    val context = LocalContext.current
    val askedBefore: MutableState<Boolean> = rememberSaveable {
        mutableStateOf(false)
    }
    val state = remember(permissionState.status, askedBefore.value) {
        when {
            permissionState.status.isGranted -> RuntimePermissionState.GRANTED
            permissionState.status.shouldShowRationale -> RuntimePermissionState.DENIED
            askedBefore.value -> RuntimePermissionState.DENIED_FOREVER
            else -> RuntimePermissionState.NEVER_ASKED
        }
    }

    PermissionPopup(
        icon = icon,
        explanation = when (state) {
            RuntimePermissionState.NEVER_ASKED -> explanation
            RuntimePermissionState.DENIED -> insistExplanation
            RuntimePermissionState.DENIED_FOREVER -> foreverDeniedExplanation
            RuntimePermissionState.GRANTED -> "ooops something went wrong" // should never happen
        },
        display = state == RuntimePermissionState.NEVER_ASKED || (required && state != RuntimePermissionState.GRANTED),
        onDismiss = {
            askedBefore.value = true
        },
        onClick = {
            when (state) {
                RuntimePermissionState.NEVER_ASKED -> {
                    askedBefore.value = true
                    permissionState.launchPermissionRequest()
                }
                RuntimePermissionState.DENIED -> {
                    permissionState.launchPermissionRequest()
                }
                RuntimePermissionState.DENIED_FOREVER -> {
                    (context as? Activity)?.finish()
                        ?: error("context is not an activity")
                }
                RuntimePermissionState.GRANTED -> {
                    // should never happen
                    error("runtime permission popup is displayed but permission is granted")
                }
            }
        }
    )
}