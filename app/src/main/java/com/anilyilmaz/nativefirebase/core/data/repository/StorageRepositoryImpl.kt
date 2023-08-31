package com.anilyilmaz.nativefirebase.core.data.repository

import com.anilyilmaz.nativefirebase.core.network.api.StorageService
import com.anilyilmaz.nativefirebase.core.network.model.Content
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class StorageRepositoryImpl @Inject constructor(private val storageService: StorageService
): StorageRepository {
    override val contents: Flow<List<Content>>
        get() = storageService.contents

    override suspend fun save(content: Content): String =
        storageService.save(content)

    override suspend fun delete(contentId: String) {
        storageService.delete(contentId)
    }
}