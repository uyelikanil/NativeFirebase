package com.anilyilmaz.nativefirebase.core.network.api

import androidx.core.os.trace
import com.anilyilmaz.nativefirebase.core.network.model.Content
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.dataObjects
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class StorageServiceImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val auth: AccountService
): StorageService {

    override val contents: Flow<List<Content>>
        get() =
            auth.currentUser.flatMapLatest { user ->
                firestore.collection(CONTENT_COLLECTION).whereEqualTo(USER_ID_FIELD,
                    user.id).dataObjects()
            }

    override suspend fun save(content: Content): String =
        trace(SAVE_TASK_TRACE) {
            val taskWithUserId = content.copy(userId = auth.currentUserId)
            firestore.collection(CONTENT_COLLECTION).add(taskWithUserId).await().id
        }

    override suspend fun delete(contentId: String) {
        firestore.collection(CONTENT_COLLECTION).document(contentId).delete().await()
    }

    companion object {
        private const val USER_ID_FIELD = "userId"
        private const val CONTENT_COLLECTION = "content"
        private const val SAVE_TASK_TRACE = "saveTask"
    }
}