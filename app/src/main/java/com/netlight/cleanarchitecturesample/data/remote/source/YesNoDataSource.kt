package com.netlight.cleanarchitecturesample.data.remote.source

import com.netlight.cleanarchitecturesample.data.remote.api.YesNoApi
import com.netlight.cleanarchitecturesample.data.remote.model.YesNoResponse
import java.io.IOException

class YesNoDataSource(private val api: YesNoApi) {

    suspend fun getAnswer(): YesNoResponse {
        val response = api.getAnswer()

        if (!response.isSuccessful) throw IOException("Unsuccessful response")

        return response.body() ?: throw IllegalStateException("Empty response body")
    }
}
