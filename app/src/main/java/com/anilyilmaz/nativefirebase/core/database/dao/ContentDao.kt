package com.anilyilmaz.nativefirebase.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.anilyilmaz.nativefirebase.core.database.entity.ContentEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ContentDao {
    @Transaction
    @Query("select * from content_table where userId = :userId")
    fun getContents(userId: String): Flow<List<ContentEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertContent(content: ContentEntity)

    @Query("DELETE from content_table WHERE id = :id")
    fun deleteContent(id: Int)
}