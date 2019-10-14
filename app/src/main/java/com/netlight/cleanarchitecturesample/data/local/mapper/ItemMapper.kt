package com.netlight.cleanarchitecturesample.data.local.mapper

import com.netlight.cleanarchitecturesample.data.local.entity.ItemEntity
import com.netlight.cleanarchitecturesample.domain.entity.Item

private fun map(item: ItemEntity) = Item(
    id = item.id,
    description = item.description
)

fun ItemEntity.toItem() = map(this)
fun List<ItemEntity>.toItem() = map { it.toItem() }

private fun map(item: Item) = ItemEntity(
    id = item.id,
    description = item.description
)

fun Item.toItemEntity() = map(this)
fun List<Item>.toItemEntity() = map { it.toItemEntity() }