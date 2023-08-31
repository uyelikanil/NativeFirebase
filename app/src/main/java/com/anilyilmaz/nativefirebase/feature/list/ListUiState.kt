package com.anilyilmaz.nativefirebase.feature.list

import com.anilyilmaz.nativefirebase.core.database.entity.ContentEntity
import com.anilyilmaz.nativefirebase.core.network.model.Content

data class ListUiState(
    val offlineContents: List<ContentEntity> = listOf(),
    val remoteContents: List<Content> = listOf()
)
