package app.demo.Vcalls.sharedPref

import android.content.Context
import android.content.SharedPreferences

class UserPreferences(context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

    companion object {
        private const val KEY_NAME = "key_name"
        private const val KEY_EMAIL = "key_email"
        private const val KEY_TOKEN = "key_token"
    }

    // Save all user info
    fun saveUser(name: String?, email: String, token : String?) {
        prefs.edit().apply {
            putString(KEY_NAME, name)
            putString(KEY_EMAIL, email)
            putString(KEY_TOKEN, token)
            apply()
        }
    }



    fun getName(): String? = prefs.getString(KEY_NAME, null)

    fun getEmail(): String? = prefs.getString(KEY_EMAIL, null)

    fun getToken(): String? = prefs.getString(KEY_TOKEN, null)

    fun clear() {
        prefs.edit().clear().apply()
    }
}
