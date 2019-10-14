package com.netlight.cleanarchitecturesample

import android.app.Application
import coil.Coil
import coil.ImageLoader
import coil.decode.GifDecoder
import com.netlight.cleanarchitecturesample.data.di.dataModules
import com.netlight.cleanarchitecturesample.domain.di.domainModules
import com.netlight.cleanarchitecturesample.presentation.di.presentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(domainModules + presentationModule + dataModules)
        }

        Coil.setDefaultImageLoader(
            ImageLoader(this) {
                componentRegistry {
                    add(GifDecoder())
                }
            }
        )
    }
}