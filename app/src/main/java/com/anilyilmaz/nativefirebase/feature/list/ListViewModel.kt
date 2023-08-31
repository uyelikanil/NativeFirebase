package com.anilyilmaz.nativefirebase.feature.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anilyilmaz.nativefirebase.core.data.repository.AccountRepository
import com.anilyilmaz.nativefirebase.core.data.repository.OfflineContentRepository
import com.anilyilmaz.nativefirebase.core.data.repository.StorageRepository
import com.anilyilmaz.nativefirebase.core.database.entity.ContentEntity
import com.anilyilmaz.nativefirebase.core.network.model.Content
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val accountRepository: AccountRepository,
    private val offlineContentRepository: OfflineContentRepository,
    private val storageRepository: StorageRepository
): ViewModel() {
    private val userId = accountRepository.currentUserId

    private val offlineContents = offlineContentRepository.getContents(userId).map{ it }

    private val remoteContents = storageRepository.contents.map{ it}

    val uiState = combine(remoteContents, offlineContents) { remoteContents, offlineContents ->
        ListUiState(remoteContents = remoteContents, offlineContents = offlineContents)
    }

    val contentState = MutableStateFlow(ListContentState())

    private val content
        get() = contentState.value.content

    fun updateUiState(uiState: ListUiState) {
        uiState.copy()
    }

    fun updateContent(content: String) {
        contentState.value = contentState.value.copy(content)
    }

    fun addContent(isRemote: Boolean) {
        if(isRemote) {
            addRemoteContent()
        } else {
            addOfflineContent()
        }
    }

    fun addOfflineContent() = viewModelScope.launch {
        val content = ContentEntity(userId = userId, content = content)
        offlineContentRepository.addContent(content)
        updateContent("")
    }

    fun deleteOfflineContent(id: Int) = viewModelScope.launch {
        offlineContentRepository.deleteContent(id)
    }

    fun addRemoteContent() = viewModelScope.launch {
        val content = Content(content = content)
        storageRepository.save(content)
        updateContent("")
    }

    fun deleteRemoteContent(contentId: String) = viewModelScope.launch {
        storageRepository.delete(contentId)
    }
}

data class ListContentState(
    val content: String = ""
)