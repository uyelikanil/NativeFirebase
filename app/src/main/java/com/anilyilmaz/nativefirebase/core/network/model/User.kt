package com.anilyilmaz.nativefirebase.core.network.model

data class User(
    val id: String = "",
    val isAnonymous: Boolean = true,
    val email: String = "",
)