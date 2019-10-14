package com.netlight.cleanarchitecturesample.data.repository

import com.netlight.cleanarchitecturesample.data.remote.source.YesNoDataSource
import com.netlight.cleanarchitecturesample.domain.entity.GifAnswer
import com.netlight.cleanarchitecturesample.domain.repository.GifRepository

class GifRepositoryImpl(private val dataSource: YesNoDataSource) : GifRepository {

    override suspend fun getGifAnswer(): GifAnswer =
        dataSource.getAnswer().let { GifAnswer(it.answer, it.image) }
}
