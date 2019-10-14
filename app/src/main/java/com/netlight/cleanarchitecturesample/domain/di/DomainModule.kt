package com.netlight.cleanarchitecturesample.domain.di

import com.netlight.cleanarchitecturesample.domain.usecase.CreateItemUseCase
import com.netlight.cleanarchitecturesample.domain.usecase.DeleteItemUseCase
import com.netlight.cleanarchitecturesample.domain.usecase.GetAnswerUseCase
import com.netlight.cleanarchitecturesample.domain.usecase.GetItemsUseCase
import org.koin.dsl.module

private val modules = module {
    single { GetAnswerUseCase(get()) }
    single { GetItemsUseCase(get()) }
    single { CreateItemUseCase(get()) }
    single { DeleteItemUseCase(get()) }
}

val domainModules = listOf(modules)