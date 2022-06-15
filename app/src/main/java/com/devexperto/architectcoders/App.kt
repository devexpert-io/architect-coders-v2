package com.devexperto.architectcoders

import android.app.Application
import com.devexperto.architectcoders.di.AppComponent
import com.devexperto.architectcoders.di.DaggerAppComponent

abstract class App<T : AppComponent> : Application() {
    lateinit var component: T
        private set

    override fun onCreate() {
        super.onCreate()

        component = initDaggerComponent()
    }

    abstract fun initDaggerComponent(): T
}

open class AppImpl : App<AppComponent>() {

    override fun initDaggerComponent() = DaggerAppComponent
        .factory()
        .create(this)
}