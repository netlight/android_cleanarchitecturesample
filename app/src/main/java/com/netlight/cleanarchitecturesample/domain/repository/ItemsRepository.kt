package com.netlight.cleanarchitecturesample.domain.repository

import com.netlight.cleanarchitecturesample.domain.entity.Item
import kotlinx.coroutines.flow.Flow

interface ItemsRepository {

    fun getItems(): Flow<List<Item>>

    suspend fun create(item: Item): Long

    suspend fun delete(itemId: Long): Boolean

}