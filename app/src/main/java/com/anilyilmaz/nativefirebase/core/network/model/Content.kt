package com.anilyilmaz.nativefirebase.core.network.model

import com.google.firebase.firestore.DocumentId

data class Content (
    @DocumentId val id: String = "",
    val userId: String = "",
    val content: String = ""
)