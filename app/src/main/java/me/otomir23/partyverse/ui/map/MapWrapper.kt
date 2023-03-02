package me.otomir23.partyverse.ui.map

import android.Manifest
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import androidx.constraintlayout.compose.ConstraintLayout
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.mapbox.geojson.Point
import com.mapbox.maps.MapView
import com.mapbox.maps.plugin.locationcomponent.location
import me.otomir23.partyverse.R

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun MapWrapper(
    providedMapView: MapView? = null,
    showLocation: Boolean = false,
    onMove: (Point) -> Unit = {},
) {
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val context = LocalContext.current
        val mapboxMap = createRef()
        val canShowLocation = rememberPermissionState(
            permission = Manifest.permission.ACCESS_FINE_LOCATION
        ).status.isGranted
        val mapView = providedMapView ?: rememberMapView()

        DisposableEffect(canShowLocation, showLocation) {
            if (canShowLocation && showLocation) {
                mapView.location.updateSettings {
                    enabled = true
                    pulsingEnabled = true
                    pulsingColor = context.getColor(R.color.purple_500)
                }
                mapView.location.addOnIndicatorPositionChangedListener(onMove)

                onDispose {
                    mapView.location.removeOnIndicatorPositionChangedListener(onMove)
                }
            } else {
                mapView.location.updateSettings {
                    enabled = false
                }

                onDispose {}
            }
        }

        AndroidView(
            factory = {mapView},
            modifier = Modifier.constrainAs(mapboxMap) {
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        )
    }
}

@Preview
@Composable
fun MapWrapperPreview() {
    MapWrapper()
}