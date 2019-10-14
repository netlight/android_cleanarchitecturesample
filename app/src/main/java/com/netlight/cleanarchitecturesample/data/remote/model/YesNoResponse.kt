package com.netlight.cleanarchitecturesample.data.remote.model

import com.google.gson.annotations.SerializedName

data class YesNoResponse(
    @SerializedName("answer")
    val answer: String,

    @SerializedName("image")
    val image: String?
)
