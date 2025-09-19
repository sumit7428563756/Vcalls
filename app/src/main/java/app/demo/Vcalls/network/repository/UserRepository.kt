package app.demo.Vcalls.network.repository

import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserRepository(
    private val supabaseClient: SupabaseClient
) {
    suspend fun getCurrentUserProfile(): Result<Pair<String, String>> {
        return withContext(Dispatchers.IO) {
            try {
                val user = supabaseClient.auth.currentUserOrNull()
                    ?: return@withContext Result.failure(Exception("No user logged in"))

                val email = user.email ?: ""
                val name = user.userMetadata?.get("name")?.toString() ?: ""

                Result.success(name to email)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
}