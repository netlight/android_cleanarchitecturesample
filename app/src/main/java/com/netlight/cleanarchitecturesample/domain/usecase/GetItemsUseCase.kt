package com.netlight.cleanarchitecturesample.domain.usecase

import com.netlight.cleanarchitecturesample.domain.entity.Item
import com.netlight.cleanarchitecturesample.domain.repository.ItemsRepository
import kotlinx.coroutines.flow.Flow

class GetItemsUseCase(private val repo: ItemsRepository) {

    fun execute(): Flow<List<Item>> {
        return repo.getItems()
    }
}