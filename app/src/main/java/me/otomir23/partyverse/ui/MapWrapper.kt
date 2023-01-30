package me.otomir23.partyverse.ui

import android.Manifest
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import androidx.constraintlayout.compose.ConstraintLayout
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.MapView
import com.mapbox.maps.plugin.MapPlugin
import com.mapbox.maps.plugin.Plugin
import com.mapbox.maps.plugin.annotation.annotations
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions
import com.mapbox.maps.plugin.annotation.generated.createPointAnnotationManager
import com.mapbox.maps.plugin.attribution.attribution
import com.mapbox.maps.plugin.compass.compass
import com.mapbox.maps.plugin.locationcomponent.location
import com.mapbox.maps.plugin.logo.logo
import com.mapbox.maps.plugin.scalebar.scalebar
import me.otomir23.partyverse.R

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun MapWrapper(showLocation: Boolean = false) {
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val mapboxMap = createRef()
        val context = LocalContext.current
        val canShowLocation = rememberPermissionState(
            permission = Manifest.permission.ACCESS_FINE_LOCATION
        ).status.isGranted

        val mapView = remember {
            MapView(context).apply {
                getMapboxMap().loadStyleUri(context.getString(R.string.mapbox_style_url))
                scalebar.enabled = false
                attribution.enabled = false
                logo.enabled = false
                compass.enabled = false

            }
        }
        val annotationManager = remember {
            mapView.annotations.createPointAnnotationManager().apply {
                create(
                    PointAnnotationOptions()
                        .withPoint(Point.fromLngLat(37.458313, 55.660513))
                        .withIconImage("marker")
                        .withTextField("здесь были убиты пять человек тринадцатого марта")
                        .withTextColor(context.getColor(R.color.white))
                        .withTextSize(12.0)
                )
            }
        }

        LaunchedEffect(canShowLocation, showLocation) {
            if (canShowLocation && showLocation) {
                mapView.location.updateSettings {
                    enabled = true
                    pulsingEnabled = true
                    pulsingColor = context.getColor(R.color.purple_500)
                }
                var locationUpdated = false
                mapView.location.addOnIndicatorPositionChangedListener {
                    if (locationUpdated) return@addOnIndicatorPositionChangedListener
                    mapView.getMapboxMap().setCamera(
                        CameraOptions.Builder()
                            .center(it)
                            .zoom(15.0)
                            .build()
                    )
                    locationUpdated = true
                }
            } else {
                mapView.location.updateSettings {
                    enabled = false
                }
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