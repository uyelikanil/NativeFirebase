package com.anilyilmaz.nativefirebase.core.data.di

import com.anilyilmaz.nativefirebase.core.data.repository.AccountRepository
import com.anilyilmaz.nativefirebase.core.data.repository.AccountRepositoryImpl
import com.anilyilmaz.nativefirebase.core.data.repository.OfflineContentRepository
import com.anilyilmaz.nativefirebase.core.data.repository.OfflineContentRepositoryImpl
import com.anilyilmaz.nativefirebase.core.data.repository.StorageRepository
import com.anilyilmaz.nativefirebase.core.data.repository.StorageRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {
    @Binds
    fun bindsAccountRepository(accountRepository: AccountRepositoryImpl): AccountRepository

    @Binds
    fun bindsStorageRepository(storageRepository: StorageRepositoryImpl): StorageRepository

    @Binds
    fun bindsOfflineContentRepository(offlineContentRepository: OfflineContentRepositoryImpl
    ): OfflineContentRepository
}