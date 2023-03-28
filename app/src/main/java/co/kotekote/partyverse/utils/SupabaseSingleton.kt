package co.kotekote.partyverse.utils

import android.content.Context
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.GoTrue
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.realtime.Realtime
import co.kotekote.partyverse.R

object SupabaseSingleton {
    private var client: SupabaseClient? = null

    fun getClient(ctx: Context): SupabaseClient {
        if (client == null) {
            client = createSupabaseClient(
                supabaseUrl = ctx.getString(R.string.supabase_url),
                supabaseKey = ctx.getString(R.string.supabase_public_token)
            ) {
                install(GoTrue)
                install(Realtime)
                install(Postgrest)
            }
        }
        return client!!
    }
}