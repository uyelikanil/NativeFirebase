package com.anilyilmaz.nativefirebase.feature.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anilyilmaz.nativefirebase.R
import com.anilyilmaz.nativefirebase.core.common.extension.isValidEmail
import com.anilyilmaz.nativefirebase.core.common.extension.isValidPassword
import com.anilyilmaz.nativefirebase.core.data.repository.AccountRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val accountRepository: AccountRepository
): ViewModel() {
    val uiState = accountRepository.currentUser.map { ProfileUiState(it.email) }

    fun onLogOutClick() = viewModelScope.launch {
        accountRepository.signOut()
    }
}