package com.netlight.cleanarchitecturesample.domain.usecase

import com.netlight.cleanarchitecturesample.domain.entity.Item
import com.netlight.cleanarchitecturesample.domain.exception.ItemNotDeletedException
import com.netlight.cleanarchitecturesample.domain.repository.ItemsRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.slot
import kotlinx.coroutines.test.runBlockingTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertFailsWith

class DeleteItemUseCaseTest {

    private val repo: ItemsRepository = mockk()

    private lateinit var sut: DeleteItemUseCase

    @BeforeEach
    fun setup() {
        sut = DeleteItemUseCase(repo)
    }

    @Test
    fun `item is deleted from repo`() = runBlockingTest {
        // Given
        val slot = slot<Long>()
        coEvery { repo.delete(capture(slot)) } returns true

        // When
        val item = Item(id = 1, description = "My item")
        sut.execute(item)

        // Then
        assertThat(slot.captured).isEqualTo(1)
    }

    @Test
    fun `non positive id throws exception`() = runBlockingTest {
        // Given
        val slot = slot<Long>()
        coEvery { repo.delete(capture(slot)) } returns false

        // When & Then
        assertFailsWith<IllegalArgumentException> {
            val item = Item(id = 0, description = "My item")
            sut.execute(item)
        }
    }

    @Test
    fun `non positive id does not call repo`() = runBlockingTest {
        // Given
        val slot = slot<Long>()
        coEvery { repo.delete(capture(slot)) } returns false

        try {
            // When
            val item = Item(id = 0, description = "My item")
            sut.execute(item)
        } catch (e: Exception) {
            // Then
            coVerify(exactly = 0) { repo.delete(any()) }
        }
    }

    @Test
    fun `when delete fails then ItemNotDeletedException is thrown`() = runBlockingTest {
        // Given
        coEvery { repo.delete(any()) } returns false

        // When & Then
        assertFailsWith<ItemNotDeletedException> {
            val item = Item(id = 1, description = "My item")
            sut.execute(item)
        }
    }
}