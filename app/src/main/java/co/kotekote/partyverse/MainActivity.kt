package co.kotekote.partyverse

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.NearMe
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import co.kotekote.partyverse.ui.map.MapWrapper
import co.kotekote.partyverse.ui.map.getDefaultCamera
import co.kotekote.partyverse.ui.map.rememberMapView
import co.kotekote.partyverse.ui.permissions.RuntimePermissionPopup
import co.kotekote.partyverse.ui.theme.PartyverseTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import com.mapbox.android.gestures.MoveGestureDetector
import com.mapbox.geojson.Point
import com.mapbox.maps.plugin.gestures.OnMoveListener
import com.mapbox.maps.plugin.gestures.gestures

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            PartyverseTheme {
                MainScreen()
            }
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class, ExperimentalMaterialApi::class)
@Composable
fun MainScreen() {
    val context = LocalContext.current
    val locationPermissionState = rememberPermissionState(
        permission = Manifest.permission.ACCESS_FINE_LOCATION
    )
    val mapView = rememberMapView()
    val lastKnownLocation = remember { mutableStateOf<Point?>(null) }
    val following = remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        mapView.gestures.addOnMoveListener(object : OnMoveListener {
            override fun onMove(detector: MoveGestureDetector): Boolean = false

            override fun onMoveBegin(detector: MoveGestureDetector) {
                following.value = false
            }

            override fun onMoveEnd(detector: MoveGestureDetector) {}
        })
    }

    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState()
    BottomSheetScaffold(
        sheetPeekHeight = 128.dp,
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    lastKnownLocation.value?.let {
                        mapView.getMapboxMap().setCamera(getDefaultCamera(it))
                    }
                    following.value = true
                },
                backgroundColor = MaterialTheme.colors.primary
            ) {
                Icon(
                    Icons.Filled.NearMe,
                    contentDescription = context.getString(R.string.center_me_button)
                )
            }
        },
        sheetContent = {
            Text(
                "Доброе утро, Женя!",
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.h5
            )
            LazyColumn {
                items(25) {
                    ListItem(
                        text = { Text("Item $it") },
                        icon = {
                            Icon(
                                Icons.Default.Favorite,
                                contentDescription = "Localized description"
                            )
                        }
                    )
                }
            }
        },
        sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        scaffoldState = bottomSheetScaffoldState
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            MapWrapper(
                showLocation = true,
                providedMapView = mapView,
                onMove = {
                    lastKnownLocation.value = it
                    if (following.value) {
                        mapView.getMapboxMap().setCamera(getDefaultCamera(it))
                    }
                }
            )
            Button(
                onClick = {
                    println("meow")
                },
                modifier = Modifier
                    .padding(16.dp)
                    .padding(top = 32.dp)
                    .align(Alignment.TopEnd)
            ) {
                Icon(
                    Icons.Filled.Person,
                    contentDescription = context.getString(R.string.profile_button)
                )
            }
        }
    }

    RuntimePermissionPopup(
        permissionState = locationPermissionState,
        explanation = context.getString(R.string.location_request),
        insistExplanation = context.getString(R.string.location_request_insist),
        foreverDeniedExplanation = context.getString(R.string.location_requset_denied),
        icon = Icons.Filled.LocationOn,
        required = true
    )
}
