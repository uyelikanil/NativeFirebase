package com.anilyilmaz.nativefirebase.core.database.di

import android.content.Context
import androidx.room.Room
import com.anilyilmaz.nativefirebase.core.database.NativeFirebaseDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun providesNativeFirebaseDatabase(
        @ApplicationContext context: Context,
    ): NativeFirebaseDatabase = Room.databaseBuilder(
        context,
        NativeFirebaseDatabase::class.java,
        "native-firebase-database",
    ).build()
}