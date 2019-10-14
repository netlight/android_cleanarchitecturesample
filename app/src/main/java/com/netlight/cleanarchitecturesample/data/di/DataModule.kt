package com.netlight.cleanarchitecturesample.data.di

import androidx.room.Room
import com.netlight.cleanarchitecturesample.BuildConfig
import com.netlight.cleanarchitecturesample.data.local.db.ItemsDatabase
import com.netlight.cleanarchitecturesample.data.local.source.ItemsDataSource
import com.netlight.cleanarchitecturesample.data.remote.api.YesNoApi
import com.netlight.cleanarchitecturesample.data.remote.source.YesNoDataSource
import com.netlight.cleanarchitecturesample.data.repository.GifRepositoryImpl
import com.netlight.cleanarchitecturesample.data.repository.ItemsRepositoryImpl
import com.netlight.cleanarchitecturesample.domain.repository.GifRepository
import com.netlight.cleanarchitecturesample.domain.repository.ItemsRepository
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private val databaseModule = module {
    single {
        Room.databaseBuilder(get(), ItemsDatabase::class.java, ItemsDatabase.DATABASE_NAME)
            .build()
    }
    single { get<ItemsDatabase>().itemDao() }
}

private val networkModule = module {
    single {
        Retrofit.Builder()
            .baseUrl(BuildConfig.API_YESNO)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    single { get<Retrofit>().create(YesNoApi::class.java) }
}

private val repositoryModule = module {
    single<ItemsRepository> { ItemsRepositoryImpl(get()) }
    single { ItemsDataSource(get()) }

    single<GifRepository> { GifRepositoryImpl(get()) }
    single { YesNoDataSource(get()) }
}

val dataModules = repositoryModule + networkModule + databaseModule