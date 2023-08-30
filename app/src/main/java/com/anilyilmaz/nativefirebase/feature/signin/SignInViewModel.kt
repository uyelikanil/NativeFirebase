package com.anilyilmaz.nativefirebase.feature.signin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anilyilmaz.nativefirebase.R
import com.anilyilmaz.nativefirebase.core.common.extension.isValidEmail
import com.anilyilmaz.nativefirebase.core.common.extension.isValidPassword
import com.anilyilmaz.nativefirebase.core.data.repository.AccountRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(private val accountRepository: AccountRepository
): ViewModel() {
    private val _uiState = MutableStateFlow<SignInUiState>(SignInUiState.SignIn)
    val uiState: StateFlow<SignInUiState> = _uiState.asStateFlow()

    val userState = MutableStateFlow(SignInUserState())

    private val email
        get() = userState.value.email
    private val password
        get() = userState.value.password

    fun updateEmail(newValue: String) {
        userState.value = userState.value.copy(email = newValue)
    }

    fun updatePassword(newValue: String) {
        userState.value = userState.value.copy(password = newValue)
    }

    fun errorHandled() {
        _uiState.value = SignInUiState.SignIn
    }

    fun hasUser() = accountRepository.hasUser

    fun onSignInClick() = viewModelScope.launch {
        _uiState.value = SignInUiState.Loading

        try {
            if (!email.isValidEmail()) {
                _uiState.value = SignInUiState.Error(R.string.email_is_not_valid)
            } else if (!password.isValidPassword()) {
                _uiState.value = SignInUiState.Error(R.string.password_is_not_valid)
            } else {
                accountRepository.authenticate(email, password)
                _uiState.value = SignInUiState.Success
            }
        } catch (e: Exception) {
            e.printStackTrace()
            _uiState.value = SignInUiState.Error(R.string.an_error_occured_while_sign_in)
        }
    }
}

data class SignInUserState(
    val email: String = "",
    val password: String = ""
)