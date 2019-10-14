package com.netlight.cleanarchitecturesample.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.netlight.cleanarchitecturesample.data.local.entity.ItemEntity
import kotlinx.coroutines.flow.Flow

@Dao
abstract class ItemDao {

    @Query("SELECT * FROM items WHERE id = :id")
    abstract suspend fun getById(id: Long): ItemEntity

    @Query("SELECT * FROM items ORDER BY description")
    abstract fun getAll(): Flow<List<ItemEntity>>

    @Query("DELETE FROM items")
    abstract suspend fun deleteAll(): Int

    @Query("DELETE FROM items WHERE id = :id")
    abstract suspend fun deleteById(id: Long): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insert(item: ItemEntity): Long

    @Delete
    abstract suspend fun delete(item: ItemEntity): Int
}
