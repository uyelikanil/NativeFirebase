package com.anilyilmaz.nativefirebase.core.data.di

import com.anilyilmaz.nativefirebase.core.data.repository.AccountRepository
import com.anilyilmaz.nativefirebase.core.data.repository.AccountRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {
    @Binds
    fun bindsAccountRepository(accountRepository: AccountRepositoryImpl): AccountRepository
}