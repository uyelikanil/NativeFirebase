package com.anilyilmaz.nativefirebase.core.common.data.di

import com.anilyilmaz.nativefirebase.core.common.data.Dispatcher
import com.anilyilmaz.nativefirebase.core.common.data.NfDispatchers
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(SingletonComponent::class)
object DispatchersModule {
    @Provides
    @Dispatcher(NfDispatchers.IO)
    fun providesIODispatcher(): CoroutineDispatcher = Dispatchers.IO
}