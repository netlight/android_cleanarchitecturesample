package com.netlight.cleanarchitecturesample.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.netlight.cleanarchitecturesample.data.local.dao.ItemDao
import com.netlight.cleanarchitecturesample.data.local.entity.ItemEntity

@Database(
    version = 1,
    entities = [ItemEntity::class]
)
abstract class ItemsDatabase : RoomDatabase() {

    abstract fun itemDao(): ItemDao

    companion object {
        const val DATABASE_NAME = "clean_architecture_sample.db"
    }
}
