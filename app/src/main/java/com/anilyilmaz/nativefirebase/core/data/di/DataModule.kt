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
abstract class  DataModule {
    @Binds
    abstract fun bindsAccountRepository(accountRepository: AccountRepositoryImpl): AccountRepository

    @Binds
    abstract fun bindsStorageRepository(storageRepository: StorageRepositoryImpl): StorageRepository

    @Binds
    abstract fun bindsOfflineContentRepository(offlineContentRepository: OfflineContentRepositoryImpl
    ): OfflineContentRepository
}