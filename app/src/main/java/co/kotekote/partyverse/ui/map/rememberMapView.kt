package co.kotekote.partyverse.ui.map

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.MapView
import com.mapbox.maps.plugin.attribution.attribution
import com.mapbox.maps.plugin.compass.compass
import com.mapbox.maps.plugin.logo.logo
import com.mapbox.maps.plugin.scalebar.scalebar
import co.kotekote.partyverse.R

@Composable
fun rememberMapView(): MapView {
    val context = LocalContext.current
    return remember {
        MapView(context).apply {
            getMapboxMap().loadStyleUri(context.getString(R.string.mapbox_style_url))
            scalebar.enabled = false
            attribution.enabled = false
            logo.enabled = false
            compass.enabled = false
        }
    }
}

fun getDefaultCamera(userPos: Point): CameraOptions {
    return CameraOptions.Builder()
        .center(userPos)
        .zoom(15.0)
        .build()
}