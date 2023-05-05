package co.kotekote.partyverse.data

import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.gotrue.SessionStatus
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Profile(
    val id: String,
    val username: String,
    @SerialName("created_at") val createdAt: String,
    val avatar: String?,
) {
    val avatarUrl
        get() = avatar?.let { generateAvatarUrl(this.id, this.avatar) }
}

suspend fun getProfile(supabaseClient: SupabaseClient, sessionStatus: SessionStatus.Authenticated): Profile {
    val result = supabaseClient.postgrest["profiles"]
        .select {
            Profile::id eq sessionStatus.session.user?.id
        }

    return result.decodeSingle()
}

fun generateAvatarUrl(userId: String, avatarId: String): String {
    return "$userId/$avatarId.jpg"
}