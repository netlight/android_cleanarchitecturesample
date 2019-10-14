package com.netlight.cleanarchitecturesample.domain.usecase

import com.netlight.cleanarchitecturesample.domain.entity.Answer
import com.netlight.cleanarchitecturesample.domain.repository.GifRepository
import java.security.InvalidParameterException

class GetAnswerUseCase(private val repo: GifRepository) {

    suspend fun execute(): Answer {
        val gifAnswer = repo.getGifAnswer()

        return when (gifAnswer.answer) {
            "yes" -> Answer.Yes(gifAnswer.imageUrl!!)
            "no" -> Answer.No(gifAnswer.imageUrl!!)
            "maybe" -> Answer.Maybe
            else -> throw InvalidParameterException("Answer is other than yes/no/maybe")
        }
    }
}