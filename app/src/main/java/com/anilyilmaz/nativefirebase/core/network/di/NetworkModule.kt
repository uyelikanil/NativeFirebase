package com.anilyilmaz.nativefirebase.core.network.di

import com.anilyilmaz.nativefirebase.core.network.api.AccountService
import com.anilyilmaz.nativefirebase.core.network.api.AccountServiceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class NetworkModule {
    @Binds
    abstract fun provideAccountService(accountService: AccountServiceImpl): AccountService
}