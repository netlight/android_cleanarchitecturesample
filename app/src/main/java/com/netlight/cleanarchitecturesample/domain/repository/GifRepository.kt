package com.netlight.cleanarchitecturesample.domain.repository

import com.netlight.cleanarchitecturesample.domain.entity.GifAnswer

interface GifRepository {

    suspend fun getGifAnswer(): GifAnswer

}