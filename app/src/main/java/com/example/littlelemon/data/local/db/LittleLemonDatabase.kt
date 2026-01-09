package com.example.littlelemon.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [MenuItemEntity::class],
    version = 1,
    exportSchema = false,
)
abstract class LittleLemonDatabase : RoomDatabase() {
    abstract fun menuItemDao(): MenuItemDao
}
