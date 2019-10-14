package com.netlight.cleanarchitecturesample.domain.usecase

import com.netlight.cleanarchitecturesample.domain.repository.ItemsRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.flowOf
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GetItemsUseCaseTest {

    private val repo: ItemsRepository = mockk()

    private lateinit var sut: GetItemsUseCase

    @BeforeEach
    fun setup() {
        sut = GetItemsUseCase(repo)
    }

    @Test
    fun `items are loaded from repo`() {
        // Given
        every { repo.getItems() } returns flowOf(emptyList())

        // When
        sut.execute()

        // Then
        verify { repo.getItems() }
    }
}