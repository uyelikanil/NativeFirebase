package com.anilyilmaz.nativefirebase.core.data.repository

import com.anilyilmaz.nativefirebase.core.database.entity.ContentEntity
import kotlinx.coroutines.flow.Flow

interface OfflineContentRepository {
    fun getContents(userId: String): Flow<List<ContentEntity>>

    suspend fun addContent(content: ContentEntity)

    suspend fun deleteContent(id: Int)
}