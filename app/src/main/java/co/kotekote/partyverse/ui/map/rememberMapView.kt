package co.kotekote.partyverse.ui.map

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import co.kotekote.partyverse.R
import co.kotekote.partyverse.ui.theme.LocalDarkThemeState
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.MapView
import com.mapbox.maps.plugin.attribution.attribution
import com.mapbox.maps.plugin.compass.compass
import com.mapbox.maps.plugin.logo.logo
import com.mapbox.maps.plugin.scalebar.scalebar

@Composable
fun rememberMapView(): MapView {
    val context = LocalContext.current
    val darkTheme = LocalDarkThemeState.current

    val mapboxStyleUri = stringResource(
        if (darkTheme) R.string.mapbox_dark_style_url
        else R.string.mapbox_light_style_url
    )

    val mapView = remember {
        MapView(context).apply {
            scalebar.enabled = false
            attribution.enabled = false
            logo.enabled = false
            compass.enabled = false
        }
    }

    LaunchedEffect(mapboxStyleUri) {
        mapView.getMapboxMap().loadStyleUri(mapboxStyleUri)
    }

    return mapView
}

fun getDefaultCamera(userPos: Point): CameraOptions {
    return CameraOptions.Builder()
        .center(userPos)
        .zoom(15.0)
        .build()
}