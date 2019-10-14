package com.netlight.cleanarchitecturesample.domain.usecase

import com.netlight.cleanarchitecturesample.domain.entity.Item
import com.netlight.cleanarchitecturesample.domain.exception.ItemNotCreatedException
import com.netlight.cleanarchitecturesample.domain.repository.ItemsRepository
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.slot
import kotlinx.coroutines.test.runBlockingTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertFailsWith

class CreateItemUseCaseTest {

    private val repo: ItemsRepository = mockk()

    private lateinit var sut: CreateItemUseCase

    @BeforeEach
    fun setup() {
        sut = CreateItemUseCase(repo)
    }

    @Test
    fun `if description is blank then IllegalArgumentException is thrown`() = runBlockingTest {
        // Given
        val description = ""

        // When & Then
        assertFailsWith<IllegalArgumentException> {
            sut.execute(description)
        }
    }

    @Test
    fun `capitalize description's first letter`() = runBlockingTest {
        // Given
        val slot = slot<Item>()
        coEvery { repo.create(capture(slot)) } returns 1

        // When
        sut.execute("my item")

        // Then
        assertThat(slot.captured).isEqualTo(Item(id = 0, description = "My item"))
    }

    @Test
    fun `when create fails then ItemNotCreatedException is thrown`() = runBlockingTest {
        // Given
        coEvery { repo.create(any()) } returns 0

        // When & Then
        assertFailsWith<ItemNotCreatedException> {
            sut.execute("test")
        }
    }
}