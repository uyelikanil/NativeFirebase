package com.anilyilmaz.nativefirebase.feature.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anilyilmaz.nativefirebase.core.data.repository.AccountRepository
import com.anilyilmaz.nativefirebase.core.data.repository.OfflineContentRepository
import com.anilyilmaz.nativefirebase.core.database.entity.ContentEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val accountRepository: AccountRepository,
    private val offlineContentRepository: OfflineContentRepository
): ViewModel() {
    private val userId = accountRepository.currentUserId

    val offlineContents = offlineContentRepository.getContents(userId).map {
        ListUiState(offlineContents = it)
    }

    val contentState = MutableStateFlow(ListContentState())

    private val content
        get() = contentState.value.content

    fun updateContent(content: String) {
        contentState.value = contentState.value.copy(content)
    }

    fun addOfflineContent() = viewModelScope.launch {
        val content = ContentEntity(userId = userId, content = content)
        offlineContentRepository.addContent(content)
        updateContent("")
    }

    fun deleteOfflineContent(id: Int) = viewModelScope.launch {
        offlineContentRepository.deleteContent(id)
    }
}

data class ListContentState(
    val content: String = ""
)