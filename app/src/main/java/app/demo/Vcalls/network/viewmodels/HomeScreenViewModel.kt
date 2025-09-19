package app.demo.Vcalls.network.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.demo.Vcalls.network.data.UserUiState
import app.demo.Vcalls.network.repository.SignUpRepository
import app.demo.Vcalls.network.repository.UserRepository
import app.demo.Vcalls.network.resource.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeScreenViewModel(
    private val repository: UserRepository
) : ViewModel() {

    private val _userState = MutableStateFlow<Resource<UserUiState>>(Resource.Idle)
    val userState: StateFlow<Resource<UserUiState>> = _userState

    init {
        loadUserProfile()
    }
    private fun loadUserProfile() {
        viewModelScope.launch {
            _userState.value = Resource.Loading
            val result = repository.getCurrentUserProfile()
            _userState.value = result.fold(
                onSuccess = { (name, email) ->
                    Resource.Success(UserUiState(name, email))
                },
                onFailure = { e ->
                    Resource.Error(e.message ?: "Unknown error")
                }
            )
        }
    }
}
