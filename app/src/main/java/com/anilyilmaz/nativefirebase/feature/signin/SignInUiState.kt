package com.anilyilmaz.nativefirebase.feature.signin

import com.anilyilmaz.nativefirebase.feature.signup.SignUpUiState

sealed interface SignInUiState {
    object SignIn: SignInUiState

    object Success: SignInUiState

    object Loading: SignInUiState

    data class Error(
        val messageId: Int
    ): SignInUiState
}
