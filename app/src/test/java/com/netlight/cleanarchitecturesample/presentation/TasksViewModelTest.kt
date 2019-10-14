package com.netlight.cleanarchitecturesample.presentation

import com.netlight.cleanarchitecturesample.domain.entity.Answer.Yes
import com.netlight.cleanarchitecturesample.domain.entity.Item
import com.netlight.cleanarchitecturesample.domain.logger.Logger
import com.netlight.cleanarchitecturesample.domain.usecase.CreateItemUseCase
import com.netlight.cleanarchitecturesample.domain.usecase.DeleteItemUseCase
import com.netlight.cleanarchitecturesample.domain.usecase.GetAnswerUseCase
import com.netlight.cleanarchitecturesample.domain.usecase.GetItemsUseCase
import com.netlight.cleanarchitecturesample.presentation.viewmodel.TasksViewModel
import com.netlight.cleanarchitecturesample.utils.InstantExecutorExtension
import com.netlight.cleanarchitecturesample.utils.getOrAwaitValue
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.just
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExperimentalCoroutinesApi
@ExtendWith(MockKExtension::class, InstantExecutorExtension::class)
class TasksViewModelTest {

    @MockK
    private lateinit var getItemsUseCase: GetItemsUseCase

    @MockK
    private lateinit var createItemsUseCase: CreateItemUseCase

    @MockK
    private lateinit var deleteItemUseCase: DeleteItemUseCase

    @MockK
    private lateinit var getAnswerUseCase: GetAnswerUseCase

    @MockK
    private lateinit var logger: Logger

    private lateinit var sut: TasksViewModel

    @BeforeEach
    fun setup() {
        every { logger.e(any(), any(), any()) } just Runs

        sut = TasksViewModel(
            getItemsUseCase,
            createItemsUseCase,
            deleteItemUseCase,
            getAnswerUseCase,
            logger
        )
    }

    @Test
    fun `should call use case to create an item`() {
        // Given
        val description = "test item"

        // When
        sut.create(description)

        // Then
        coVerify { createItemsUseCase.execute("test item") }
    }

    @Test
    fun `should call use case to delete an item`() {
        // Given
        val item = Item(id = 0, description = "test item")

        // When
        sut.delete(item)

        // Then
        coVerify { deleteItemUseCase.execute(Item(id = 0, description = "test item")) }
    }

    @Test
    fun `should call use case to get an answer`() {
        // When
        sut.getAnswer()

        // Then
        coVerify { getAnswerUseCase.execute() }
    }

    @Test
    fun `should get an answer`() {
        // Then
        coEvery { getAnswerUseCase.execute() } returns Yes("yayaya")

        // When
        sut.getAnswer()

        // Then
        assertThat(sut.answers.getOrAwaitValue()).isEqualTo(Yes("yayaya"))
    }
}
