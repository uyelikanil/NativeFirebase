package com.anilyilmaz.nativefirebase.core.data.repository

import com.anilyilmaz.nativefirebase.core.network.api.AccountService
import com.anilyilmaz.nativefirebase.core.network.model.User
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AccountRepositoryImpl @Inject constructor(private val accountService: AccountService
): AccountRepository {
    override val currentUserId: String
        get() = accountService.currentUserId
    override val hasUser: Boolean
        get() = accountService.hasUser
    override val currentUser: Flow<User>
        get() = accountService.currentUser

    override suspend fun authenticate(email: String, password: String) {
        accountService.authenticate(email, password)
    }

    override suspend fun createAnonymousAccount() {
        accountService.createAnonymousAccount()
    }

    override suspend fun linkAccount(email: String, password: String) {
        accountService.linkAccount(email, password)
    }

    override suspend fun signOut() {
        accountService.signOut()
    }
}