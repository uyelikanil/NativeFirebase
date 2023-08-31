package com.anilyilmaz.nativefirebase.core.database.di

import com.anilyilmaz.nativefirebase.core.database.NativeFirebaseDatabase
import com.anilyilmaz.nativefirebase.core.database.dao.ContentDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DaoModule {
    @Provides
    fun providesTopicsDao(
        database: NativeFirebaseDatabase,
    ): ContentDao = database.contentDao()
}