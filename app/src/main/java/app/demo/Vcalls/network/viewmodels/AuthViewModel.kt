package app.demo.Vcalls.network.viewmodels



import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.demo.Vcalls.network.repository.Repository
import app.demo.Vcalls.network.repository.SignUpRepository
import app.demo.Vcalls.network.resource.Resource
import app.demo.Vcalls.sharedPref.UserPreferences

import io.github.jan.supabase.auth.exception.AuthRestException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AuthViewModel(
    private val loginRepository: Repository,
    private val signUpRepository: SignUpRepository
) : ViewModel() {

    private val _loginState = MutableStateFlow<Resource<Pair<String, String?>>>(Resource.Idle)
    val loginState: StateFlow<Resource<Pair<String, String?>>> = _loginState.asStateFlow()

    private val _signUpState = MutableStateFlow<Resource<Unit>>(Resource.Idle)
    val signUpState: StateFlow<Resource<Unit>> = _signUpState.asStateFlow()

    private val _logoutState = MutableStateFlow<Resource<Unit>>(Resource.Idle)
    val logoutState: StateFlow<Resource<Unit>> = _logoutState.asStateFlow()

    fun login(name: String,email: String, password: String,context: Context) {
        viewModelScope.launch {
            _loginState.value = Resource.Loading
            try {
                val result = loginRepository.loginUser(name, email, password)
                _loginState.value = result.fold(
                    onSuccess = { (userId, name, token) ->
                        val prefs = UserPreferences(context)
                        prefs.saveUser(
                            name = name,
                            email = email,
                            token = token
                        )
                        
                        Log.d("AuthViewModel", "Saved Token: ${prefs.getToken()}")

                        Resource.Success(userId to name)
                    },
                    onFailure = { Resource.Error(it.message ?: "Login failed") }
                )
            } catch (e: Exception) {
                _loginState.value = Resource.Error(e.message ?: "Login failed due to unexpected error")
            }
        }

    }

    fun signUp(name: String, email: String, password: String,context: Context) {
        viewModelScope.launch {
            _signUpState.value = Resource.Loading
            try {
                val result = signUpRepository.signUp(email, password, name)
                _signUpState.value = result.fold(
                    onSuccess = {
                        
                        Log.d("AuthViewModel", "Signed up -> Saved locally: $name, $email")

                        Resource.Success(Unit) },

                    onFailure = { Resource.Error(it.message ?: "Signup failed") }
                )
            } catch (e: AuthRestException) {
                // Handle Supabase-specific errors like rate limit
                val msg = if (e.message?.contains("over_email_send_rate_limit") == true) {
                    "Please wait a few seconds before resending the email."
                } else {
                    e.message ?: "Signup failed"
                }
                _signUpState.value = Resource.Error(msg)
            } catch (e: Exception) {
                _signUpState.value = Resource.Error(e.message ?: "Signup failed due to unexpected error")
            }
        }
    }

    fun verifyEmail(email: String, otp: String) {
        viewModelScope.launch {
            _signUpState.value = Resource.Loading
            try {
                val result = signUpRepository.verifyEmail(email, otp)
                _signUpState.value = result.fold(
                    onSuccess = { Resource.Success(Unit) },
                    onFailure = { Resource.Error(it.message ?: "Email verification failed") }
                )
            } catch (e: AuthRestException) {
                val msg = if (e.message?.contains("over_email_send_rate_limit") == true) {
                    "Please wait a few seconds before resending the email."
                } else {
                    e.message ?: "Email verification failed"
                }
                _signUpState.value = Resource.Error(msg)
            } catch (e: Exception) {
                _signUpState.value = Resource.Error(e.message ?: "Email verification failed due to unexpected error")
            }
        }
    }

    fun logout(context: Context) {
        viewModelScope.launch {
            _logoutState.value = Resource.Loading
            try {
                val result = loginRepository.logoutUser() // call repository suspend function

                if (result.isSuccess) {
                    UserPreferences(context).clear()
                    _logoutState.value = Resource.Success(Unit)
                } else {
                    _logoutState.value = Resource.Error(result.exceptionOrNull()?.message ?: "Logout failed")
                }
            } catch (e: Exception) {
                _logoutState.value = Resource.Error(e.message ?: "Logout failed")
            }
        }
    }
}

