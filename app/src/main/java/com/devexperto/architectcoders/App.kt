package com.devexperto.architectcoders

import android.app.Application
import com.devexperto.architectcoders.di.AppComponent
import com.devexperto.architectcoders.di.DaggerAppComponent

class App : Application() {

    lateinit var component: AppComponent
        private set

    override fun onCreate() {
        super.onCreate()

        component = DaggerAppComponent
            .factory()
            .create(this)
    }
}