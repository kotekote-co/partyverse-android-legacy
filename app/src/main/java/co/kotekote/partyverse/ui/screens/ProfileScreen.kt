package co.kotekote.partyverse.ui.screens

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import co.kotekote.partyverse.R
import co.kotekote.partyverse.data.Profile
import co.kotekote.partyverse.data.generateAvatarUrl
import co.kotekote.partyverse.data.getProfile
import co.kotekote.partyverse.data.supabase.rememberSupabaseClient
import co.kotekote.partyverse.ui.navigation.NavActions
import coil.compose.AsyncImage
import io.github.jan.supabase.gotrue.SessionStatus
import io.github.jan.supabase.gotrue.gotrue
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.storage.storage
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.util.UUID

@Composable
fun ProfileScreen(navActions: NavActions) {
    val supabaseClient = rememberSupabaseClient()
    val scope = rememberCoroutineScope()
    val sessionStatus by supabaseClient.gotrue.sessionStatus.collectAsState()
    val currentStatus = sessionStatus
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    var selectedImageUri by remember {
        mutableStateOf<Uri?>(null)
    }
    var serverAvatarData by remember {
        mutableStateOf<ByteArray?>(null)
    }

    LaunchedEffect(sessionStatus, supabaseClient, currentStatus) {
        if (currentStatus !is SessionStatus.Authenticated) return@LaunchedEffect
        scope.launch {
            val profile = getProfile(supabaseClient, currentStatus)
            profile.avatarUrl?.let {
                val data = supabaseClient.storage["avatars"].downloadAuthenticated(it)
                serverAvatarData = data
            }
        }
    }

    val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            if (uri == null) return@rememberLauncherForActivityResult
            selectedImageUri = uri
            val bitmap =
                if (Build.VERSION.SDK_INT < 28) {
                    @Suppress("DEPRECATION")
                    MediaStore.Images.Media.getBitmap(
                        context.contentResolver,
                        uri
                    )
                } else {
                    val source = ImageDecoder.createSource(context.contentResolver, uri)
                    ImageDecoder.decodeBitmap(source)
                }
            coroutineScope.launch {
                val stream = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.JPEG, 60, stream)
                if (currentStatus is SessionStatus.Authenticated) {
                    val profile = getProfile(supabaseClient, currentStatus)
                    val uuid = UUID.randomUUID().toString()

                    supabaseClient.storage["avatars"].upload(generateAvatarUrl(
                        profile.id, uuid
                    ), stream.toByteArray())

                    val avatarUrl = profile.avatarUrl
                    if (avatarUrl != null) supabaseClient.storage["avatars"].delete(avatarUrl)

                    supabaseClient.postgrest["profiles"]
                        .update(
                            {
                                Profile::avatar setTo uuid
                            }
                        ) {
                            Profile::id eq profile.id
                        }
                }
            }
        }
    )

    Column(
        Modifier
            .statusBarsPadding()
            .padding(16.dp, 0.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
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
                    Icon(
                        Icons.Default.SignalWifiConnectedNoInternet4,
                        stringResource(R.string.error_connection_short)
                    )
                    Text(
                        stringResource(R.string.error_connection_short),
                        style = MaterialTheme.typography.h5
                    )
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

        if (currentStatus is SessionStatus.Authenticated) {
            AsyncImage(
                model = selectedImageUri ?: serverAvatarData,
                contentDescription = null,
                modifier = Modifier
                    .padding(top = 75.dp)
                    .size(150.dp)
                    .clip(RoundedCornerShape(100.dp))
            )
            Button(modifier = Modifier
                .padding(top = 30.dp),
                onClick = {
                    singlePhotoPickerLauncher.launch(
                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                    )
                }) { Text(text = "change image") }
        }
    }
}
