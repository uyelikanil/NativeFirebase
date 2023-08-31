package com.anilyilmaz.nativefirebase.core.data.repository

import com.anilyilmaz.nativefirebase.core.network.model.Content
import kotlinx.coroutines.flow.Flow

interface StorageRepository {
    val contents: Flow<List<Content>>
    suspend fun save(content: Content): String
    suspend fun delete(contentId: String)
}