package com.devexperto.architectcoders.di

import android.app.Application
import com.devexperto.architectcoders.ui.MainInstrumentationTest
import dagger.BindsInstance
import dagger.Component
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Singleton

@ExperimentalCoroutinesApi
@Singleton
@Component(modules = [TestAppModule::class, AppDataModule::class])
interface TestAppComponent : AppComponent {

    fun inject(test: MainInstrumentationTest)

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance app: Application): TestAppComponent
    }
}