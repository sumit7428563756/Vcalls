package app.demo.Vcalls.network.repository
import android.content.Context
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.user.UserInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import io.github.jan.supabase.auth.OtpType
import io.github.jan.supabase.auth.providers.builtin.Email



class Repository(
    private val context: Context,
    private val supabaseClient: SupabaseClient
) {

    suspend fun loginUser(name: String, email: String, password: String): Result<Triple<String, String?, String?>> {
        return withContext(Dispatchers.IO) {
            try {
                // Sign in using Supabase Auth with the Email provider.
                supabaseClient.auth.signInWith(Email) {
                    this.email = email
                    this.password = password
                }

                // Retrieve the current session and user.
                val session = supabaseClient.auth.currentSessionOrNull()
                val user = supabaseClient.auth.retrieveUserForCurrentSession()

                if (user != null && session != null) {
                    val userId = user.id
                    val name = user.userMetadata?.get("name")?.toString()
                    val token = session.accessToken
                    Result.success(Triple(userId, name, token))
                } else {
                    Result.failure(Exception("Invalid credentials. Please try again."))
                }
            } catch (e: Exception) {
                if (e.message?.contains("Email not confirmed") == true) {
                    supabaseClient.auth.resendEmail(OtpType.Email.SIGNUP, email)
                    Result.failure(Exception("Email not verified. Verification email sent."))
                } else {
                    Result.failure(e)
                }
            }
        }
    }

    suspend fun logoutUser(): Result<Unit> {
        return withContext(Dispatchers.IO) {
            try {
                supabaseClient.auth.signOut()
                Result.success(Unit)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
}
