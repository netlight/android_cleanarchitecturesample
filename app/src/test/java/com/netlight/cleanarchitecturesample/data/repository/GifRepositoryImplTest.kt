package com.netlight.cleanarchitecturesample.data.repository

import com.netlight.cleanarchitecturesample.data.remote.model.YesNoResponse
import com.netlight.cleanarchitecturesample.data.remote.source.YesNoDataSource
import com.netlight.cleanarchitecturesample.domain.entity.GifAnswer
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExperimentalCoroutinesApi
@ExtendWith(MockKExtension::class)
internal class GifRepositoryImplTest {

    @MockK(relaxed = true)
    private lateinit var dataSource: YesNoDataSource

    private lateinit var sut: GifRepositoryImpl

    @BeforeEach
    fun setup() {
        sut = GifRepositoryImpl(dataSource)
    }

    @Test
    fun `getGifAnswer returns gif answer`() = runBlockingTest {
        // Given
        coEvery { dataSource.getAnswer() } returns YesNoResponse(
            answer = "yas",
            image = "awesomeGif"
        )

        // When
        val answer = sut.getGifAnswer()

        // Then
        assertThat(answer).isEqualTo(GifAnswer(answer = "yas", imageUrl = "awesomeGif"))
    }

    @Test
    fun `getGifAnswer calls data source`() = runBlockingTest {
        // Given
        coEvery { dataSource.getAnswer() } returns YesNoResponse("", "")

        // When
        sut.getGifAnswer()

        // Then
        coVerify { dataSource.getAnswer() }
    }
}
