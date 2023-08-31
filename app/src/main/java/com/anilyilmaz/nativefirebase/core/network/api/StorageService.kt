package com.anilyilmaz.nativefirebase.core.network.api

import com.anilyilmaz.nativefirebase.core.network.model.Content
import kotlinx.coroutines.flow.Flow

interface StorageService {
    val contents: Flow<List<Content>>
    suspend fun save(content: Content): String
    suspend fun delete(contentId: String)
}