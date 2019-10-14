package com.netlight.cleanarchitecturesample.data.repository

import com.netlight.cleanarchitecturesample.data.local.entity.ItemEntity
import com.netlight.cleanarchitecturesample.data.local.source.ItemsDataSource
import com.netlight.cleanarchitecturesample.domain.entity.Item
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExperimentalCoroutinesApi
@ExtendWith(MockKExtension::class)
internal class ItemsRepositoryImplTest {

    @MockK(relaxed = true)
    private lateinit var dataSource: ItemsDataSource

    private lateinit var sut: ItemsRepositoryImpl

    @BeforeEach
    fun setup() {
        sut = ItemsRepositoryImpl(dataSource)
    }

    @Test
    fun `getItems calls data source`() {
        // When
        sut.getItems()

        // Then
        coVerify { dataSource.getItems() }
    }

    @Test
    fun `create calls data source`() = runBlockingTest {
        // When
        sut.create(Item(id = 0L, description = "testDescription"))

        // Then
        coVerify { dataSource.insert(ItemEntity(id = 0L, description = "testDescription")) }
    }

    @Test
    fun `delete calls data source`() = runBlockingTest {
        // When
        sut.delete(1L)

        // Then
        coVerify { dataSource.deleteItem(1L) }
    }
}
