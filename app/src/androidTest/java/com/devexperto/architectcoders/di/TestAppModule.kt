package com.devexperto.architectcoders.di

import android.app.Application
import com.devexperto.architectcoders.appTestShared.FakeMovieDao
import com.devexperto.architectcoders.appTestShared.FakeRemoteService
import com.devexperto.architectcoders.appTestShared.buildRemoteMovies
import com.devexperto.architectcoders.R
import com.devexperto.architectcoders.data.database.MovieDao
import com.devexperto.architectcoders.data.server.RemoteService
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [AppModule::class]
)
object TestAppModule {

    @Provides
    @Singleton
    @ApiKey
    fun provideApiKey(app: Application): String = app.getString(R.string.api_key)

    @Provides
    @Singleton
    fun provideMovieDao(): MovieDao = FakeMovieDao()

    @Provides
    @Singleton
    fun provideRemoteService(): RemoteService =
        FakeRemoteService(buildRemoteMovies(1, 2, 3, 4, 5, 6))

}