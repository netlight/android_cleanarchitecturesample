package com.netlight.cleanarchitecturesample.data.repository

import com.netlight.cleanarchitecturesample.data.local.mapper.toItem
import com.netlight.cleanarchitecturesample.data.local.mapper.toItemEntity
import com.netlight.cleanarchitecturesample.data.local.source.ItemsDataSource
import com.netlight.cleanarchitecturesample.domain.entity.Item
import com.netlight.cleanarchitecturesample.domain.repository.ItemsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ItemsRepositoryImpl(private val dataSource: ItemsDataSource) : ItemsRepository {

    override fun getItems(): Flow<List<Item>> = dataSource.getItems().map { it.toItem() }

    override suspend fun create(item: Item): Long = dataSource.insert(item.toItemEntity())

    override suspend fun delete(itemId: Long): Boolean = dataSource.deleteItem(itemId)
}
