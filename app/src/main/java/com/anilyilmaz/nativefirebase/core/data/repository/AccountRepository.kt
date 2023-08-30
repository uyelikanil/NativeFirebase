package com.anilyilmaz.nativefirebase.core.data.repository

import com.anilyilmaz.nativefirebase.core.network.model.User
import kotlinx.coroutines.flow.Flow

interface AccountRepository {
    val currentUserId: String
    val hasUser: Boolean

    val currentUser: Flow<User>
    suspend fun authenticate(email: String, password: String)
    suspend fun createAnonymousAccount()
    suspend fun linkAccount(email: String, password: String)
    suspend fun signOut()
}