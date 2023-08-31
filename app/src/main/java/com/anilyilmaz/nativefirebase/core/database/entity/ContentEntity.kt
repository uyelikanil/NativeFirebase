package com.anilyilmaz.nativefirebase.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import javax.annotation.Nonnull

@Entity ( tableName = "content_table" )
data class ContentEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val userId: String,
    val content: String
)