package com.anilyilmaz.nativefirebase.feature.signup

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
class SignUpViewModel @Inject constructor(
    private val accountRepository: AccountRepository
): ViewModel() {
    private val _uiState = MutableStateFlow<SignUpUiState>(SignUpUiState.SignUp)
    val uiState: StateFlow<SignUpUiState> = _uiState.asStateFlow()

    val userState = MutableStateFlow(SignUpUserState())

    private val email
        get() = userState.value.email
    private val password
        get() = userState.value.password

    val hasUser = accountRepository.hasUser

    fun updateEmail(newValue: String) {
        userState.value = userState.value.copy(email = newValue)
    }

    fun updatePassword(newValue: String) {
        userState.value = userState.value.copy(password = newValue)
    }

    fun errorHandled() {
        _uiState.value = SignUpUiState.SignUp
    }

    fun onSignUpClick() = viewModelScope.launch {
        _uiState.value = SignUpUiState.Loading

        try {
            if (!email.isValidEmail()) {
                _uiState.value = SignUpUiState.Error(R.string.email_is_not_valid)
            } else if (!password.isValidPassword()) {
                _uiState.value = SignUpUiState.Error(R.string.password_is_not_valid)
            } else {
                accountRepository.createAnonymousAccount()
                accountRepository.linkAccount(email, password)
                _uiState.value = SignUpUiState.Success
            }
        } catch (e: Exception) {
            e.printStackTrace()
            _uiState.value = SignUpUiState.Error(R.string.an_error_occured_while_sign_up)
        }
    }
}

data class SignUpUserState(
    val email: String = "",
    val password: String = ""
)