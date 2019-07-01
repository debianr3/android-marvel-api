package com.igmob.android.marvelapi.application

import android.app.Application
import com.facebook.drawee.backends.pipeline.Fresco
import com.igmob.android.marvelapi.infrastructure.core.networking.NetworkingModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        Fresco.initialize(this);

        startKoin {
            androidContext(this@MainApplication)
            modules(NetworkingModule)
        }
    }
}