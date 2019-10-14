package com.netlight.cleanarchitecturesample.data.local.source

import com.netlight.cleanarchitecturesample.data.local.dao.ItemDao
import com.netlight.cleanarchitecturesample.data.local.entity.ItemEntity
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExperimentalCoroutinesApi
@ExtendWith(MockKExtension::class)
internal class ItemsDataSourceTest {

    @MockK
    private lateinit var dao: ItemDao

    private lateinit var sut: ItemsDataSource

    @BeforeEach
    fun setUp() {
        sut = ItemsDataSource(dao)
    }

    @Test
    fun `getItems calls dao`() {
        // Given
        coEvery { dao.getAll() } returns flowOf(emptyList())

        // When
        sut.getItems()

        // Then
        verify { dao.getAll() }
    }

    @Test
    fun `insert calls dao`() = runBlockingTest {
        // Given
        coEvery { dao.insert(any()) } returns 1L

        // When
        sut.insert(ItemEntity(id = 0L, description = "testDescription"))

        // Then
        coVerify { dao.insert(ItemEntity(id = 0L, description = "testDescription")) }
    }

    @Test
    fun `getItem calls dao`() = runBlockingTest {
        // Given
        coEvery { dao.getById(1L) } returns ItemEntity(id = 1L, description = "testDescription")

        // When
        sut.getItem(1L)

        // Then
        coVerify { dao.getById(1L) }
    }

    @Test
    fun `deleteItem calls dao`() = runBlockingTest {
        // Given
        coEvery { dao.deleteById(1L) } returns 1

        // When
        sut.deleteItem(1L)

        // Then
        coVerify { dao.deleteById(1L) }
    }
}
