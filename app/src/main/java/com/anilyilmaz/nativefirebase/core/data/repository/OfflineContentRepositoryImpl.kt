package com.anilyilmaz.nativefirebase.core.data.repository

import com.anilyilmaz.nativefirebase.core.common.data.Dispatcher
import com.anilyilmaz.nativefirebase.core.common.data.NfDispatchers
import com.anilyilmaz.nativefirebase.core.database.dao.ContentDao
import com.anilyilmaz.nativefirebase.core.database.entity.ContentEntity
import com.anilyilmaz.nativefirebase.core.network.api.AccountService
import com.anilyilmaz.nativefirebase.core.network.model.User
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class OfflineContentRepositoryImpl @Inject constructor(
    private val contentDao: ContentDao,
    @Dispatcher(NfDispatchers.IO) private val ioDispatcher: CoroutineDispatcher
): OfflineContentRepository {

    override fun getContents(userId: String): Flow<List<ContentEntity>> =
        contentDao.getContents(userId)

    override suspend fun addContent(content: ContentEntity) = withContext(ioDispatcher) {
        contentDao.insertContent(content)
    }

    override suspend fun deleteContent(id: Int) = withContext(ioDispatcher) {
        contentDao.deleteContent(id)
    }

}