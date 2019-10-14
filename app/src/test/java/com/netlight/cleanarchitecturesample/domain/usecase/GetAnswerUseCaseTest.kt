package com.netlight.cleanarchitecturesample.domain.usecase

import com.netlight.cleanarchitecturesample.domain.entity.Answer
import com.netlight.cleanarchitecturesample.domain.entity.GifAnswer
import com.netlight.cleanarchitecturesample.domain.repository.GifRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runBlockingTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.security.InvalidParameterException
import kotlin.test.assertFailsWith

class GetAnswerUseCaseTest {

    private val repo: GifRepository = mockk()

    private lateinit var sut: GetAnswerUseCase

    @BeforeEach
    fun setup() {
        sut = GetAnswerUseCase(repo)
    }

    @Test
    fun `when answer is 'yes' then return Yes object`() = runBlockingTest {
        // Given
        coEvery { repo.getGifAnswer() } returns GifAnswer(answer = "yes", imageUrl = "/url")

        // When
        val value = sut.execute()

        // Then
        assertThat(value).isEqualTo(Answer.Yes(imageUrl = "/url"))
    }

    @Test
    fun `when answer is 'no' then return No object`() = runBlockingTest {
        // Given
        coEvery { repo.getGifAnswer() } returns GifAnswer(answer = "no", imageUrl = "/url")

        // When
        val value = sut.execute()

        // Then
        assertThat(value).isEqualTo(Answer.No(imageUrl = "/url"))
    }

    @Test
    fun `when answer is 'maybe' then return Maybe object`() = runBlockingTest {
        // Given
        coEvery { repo.getGifAnswer() } returns GifAnswer(answer = "maybe")

        // When
        val value = sut.execute()

        // Then
        assertThat(value).isEqualTo(Answer.Maybe)
    }

    @Test
    fun `when answer is not yes-no-maybe then throw exception`() = runBlockingTest {
        // Given
        coEvery { repo.getGifAnswer() } returns GifAnswer(answer = "other")

        // When & Then
        assertFailsWith<InvalidParameterException> {
            sut.execute()
        }
    }
}