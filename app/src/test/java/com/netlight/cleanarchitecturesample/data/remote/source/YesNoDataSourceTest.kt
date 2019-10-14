package com.netlight.cleanarchitecturesample.data.remote.source

import com.netlight.cleanarchitecturesample.data.remote.api.YesNoApi
import com.netlight.cleanarchitecturesample.data.remote.model.YesNoResponse
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import okhttp3.ResponseBody
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import retrofit2.Response.error
import retrofit2.Response.success
import java.io.IOException
import kotlin.test.assertFailsWith

@ExperimentalCoroutinesApi
@ExtendWith(MockKExtension::class)
internal class YesNoDataSourceTest {

    @MockK
    private lateinit var api: YesNoApi

    private lateinit var sut: YesNoDataSource

    @BeforeEach
    fun setUp() {
        sut = YesNoDataSource(api)
    }

    @Test
    fun `getAnswer returns successful response`() = runBlockingTest {
        // Given
        coEvery { api.getAnswer() } returns success(
            YesNoResponse(
                answer = "YASSSS",
                image = "fancy/gif"
            )
        )

        // When
        val response = sut.getAnswer()

        // Then
        assertThat(response).isEqualTo(YesNoResponse(answer = "YASSSS", image = "fancy/gif"))
    }

    @Test
    fun `getAnswer with empty body in response throws exception`() = runBlockingTest {
        // Given
        coEvery { api.getAnswer() } returns success(null)

        // When & Then
        assertFailsWith<IllegalStateException>("Empty response body") {
            sut.getAnswer()
        }
    }

    @Test
    fun `getAnswer with unsuccessful response throws exception`() = runBlockingTest {
        // Given
        coEvery { api.getAnswer() } returns error(
            400,
            ResponseBody.create(null, ByteArray(0))
        )

        // When & Then
        assertFailsWith<IOException>("Unsuccessful response") {
            sut.getAnswer()
        }
    }
}
