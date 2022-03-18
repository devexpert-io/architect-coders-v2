package com.devexperto.architectcoders.di

import android.app.Application
import com.devexperto.architectcoders.ui.detail.DetailFragmentComponent
import com.devexperto.architectcoders.ui.detail.DetailFragmentModule
import com.devexperto.architectcoders.ui.main.MainFragmentComponent
import com.devexperto.architectcoders.ui.main.MainFragmentModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, AppDataModule::class])
interface AppComponent {

    fun plus(module: MainFragmentModule): MainFragmentComponent
    fun plus(module: DetailFragmentModule): DetailFragmentComponent

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance app: Application): AppComponent
    }
}