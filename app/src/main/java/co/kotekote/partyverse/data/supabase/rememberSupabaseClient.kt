package co.kotekote.partyverse.data.supabase

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import io.github.jan.supabase.SupabaseClient

@Composable
fun rememberSupabaseClient(): SupabaseClient {
    val context = LocalContext.current
    val supabaseClient = remember(context) {
        SupabaseSingleton.getClient(context)
    }

    return supabaseClient
}