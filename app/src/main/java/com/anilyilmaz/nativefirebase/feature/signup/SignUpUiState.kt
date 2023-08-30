package com.anilyilmaz.nativefirebase.feature.signup

sealed interface SignUpUiState {
    object SignUp: SignUpUiState

    object Success: SignUpUiState

    object Loading: SignUpUiState

    data class Error(
        val messageId: Int
    ): SignUpUiState
}
