package com.anilyilmaz.nativefirebase.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.anilyilmaz.nativefirebase.core.database.dao.ContentDao
import com.anilyilmaz.nativefirebase.core.database.entity.ContentEntity

@Database(
    entities = [ ContentEntity::class ],
    version = 1,
    exportSchema = true,
)
abstract class NativeFirebaseDatabase : RoomDatabase() {
    abstract fun contentDao(): ContentDao
}