package me.otomir23.partyverse.ui.map

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.mapbox.maps.MapView
import com.mapbox.maps.plugin.attribution.attribution
import com.mapbox.maps.plugin.compass.compass
import com.mapbox.maps.plugin.logo.logo
import com.mapbox.maps.plugin.scalebar.scalebar
import me.otomir23.partyverse.R

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