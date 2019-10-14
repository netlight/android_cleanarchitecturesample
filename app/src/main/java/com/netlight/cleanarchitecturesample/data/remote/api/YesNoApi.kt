package com.netlight.cleanarchitecturesample.data.remote.api

import com.netlight.cleanarchitecturesample.data.remote.model.YesNoResponse
import retrofit2.Response
import retrofit2.http.GET

interface YesNoApi {

    @GET("/api")
    suspend fun getAnswer(): Response<YesNoResponse>
}