package com.anilyilmaz.nativefirebase.feature.list

import com.anilyilmaz.nativefirebase.core.database.entity.ContentEntity

data class ListUiState(
    val offlineContents: List<ContentEntity> = listOf()
)
