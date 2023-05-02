package co.kotekote.partyverse.data.supabase

import android.content.Context
import co.kotekote.partyverse.R
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.GoTrue
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.realtime.Realtime
import io.github.jan.supabase.storage.Storage

object SupabaseSingleton {
    private var client: SupabaseClient? = null

    fun getClient(ctx: Context): SupabaseClient {
        if (client == null) {
            client = createSupabaseClient(
                supabaseUrl = ctx.getString(R.string.supabase_url),
                supabaseKey = ctx.getString(R.string.supabase_public_token)
            ) {
                install(GoTrue) {
                    scheme = "co.kotekote.partyverse"
                    host = "auth"
                }
                install(Realtime)
                install(Postgrest)
                install(Storage)
            }
        }
        return client!!
    }
}