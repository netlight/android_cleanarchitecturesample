package com.netlight.cleanarchitecturesample.domain.usecase

import com.netlight.cleanarchitecturesample.domain.entity.Item
import com.netlight.cleanarchitecturesample.domain.exception.ItemNotDeletedException
import com.netlight.cleanarchitecturesample.domain.repository.ItemsRepository

class DeleteItemUseCase(private val repo: ItemsRepository) {

    suspend fun execute(item: Item) {
        require(item.id > 0) { "Item does not have a valid Id" }

        val deleted = repo.delete(item.id)

        if (!deleted) throw ItemNotDeletedException("Item could not be deleted")
    }
}