package com.netlight.cleanarchitecturesample.domain.usecase

import com.netlight.cleanarchitecturesample.domain.entity.Item
import com.netlight.cleanarchitecturesample.domain.exception.ItemNotCreatedException
import com.netlight.cleanarchitecturesample.domain.repository.ItemsRepository

class CreateItemUseCase(private val repo: ItemsRepository) {

    suspend fun execute(description: String) {
        require(description.isNotBlank()) { "Item description must not be blank" }

        val itemToStore = Item(id = 0, description = description.capitalize())
        val id = repo.create(itemToStore)

        if (id <= 0) throw ItemNotCreatedException("Item could not be saved")
    }
}