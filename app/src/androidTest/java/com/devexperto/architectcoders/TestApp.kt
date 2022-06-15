package com.devexperto.architectcoders

import com.devexperto.architectcoders.di.DaggerTestAppComponent
import com.devexperto.architectcoders.di.TestAppComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class TestApp : App<TestAppComponent>() {

    override fun initDaggerComponent(): TestAppComponent {
        return DaggerTestAppComponent
            .factory()
            .create(this)
    }
}