package com.devexperto.architectcoders

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.devexperto.architectcoders.data.DataModule
import com.devexperto.architectcoders.data.database.MovieDatabase
import com.devexperto.architectcoders.usecases.UseCasesModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.core.annotation.Named
import org.koin.core.annotation.Single
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.ksp.generated.module

@Module
@ComponentScan
class AppModule {

    @Single
    @Named("apiKey")
    fun apiKey(ctx: Context) = ctx.getString(R.string.api_key)

    @Single
    fun movieDatabase(ctx: Context) = Room.databaseBuilder(
        ctx,
        MovieDatabase::class.java, "movie-db"
    ).build()

    @Single
    fun movieDao(db: MovieDatabase) = db.movieDao()
}

fun Application.initDI() {
    startKoin {
        androidLogger(Level.ERROR)
        androidContext(this@initDI)
        modules(AppModule().module, DataModule().module, UseCasesModule().module)
    }
}