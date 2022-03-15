package com.devexperto.architectcoders

import android.app.Application
import androidx.room.Room
import com.devexperto.architectcoders.data.*
import com.devexperto.architectcoders.data.database.MovieDatabase
import com.devexperto.architectcoders.data.database.MovieRoomDataSource
import com.devexperto.architectcoders.data.datasource.LocationDataSource
import com.devexperto.architectcoders.data.datasource.MovieLocalDataSource
import com.devexperto.architectcoders.data.datasource.MovieRemoteDataSource
import com.devexperto.architectcoders.data.server.MovieServerDataSource
import com.devexperto.architectcoders.ui.detail.DetailViewModel
import com.devexperto.architectcoders.ui.main.MainViewModel
import com.devexperto.architectcoders.usecases.FindMovieUseCase
import com.devexperto.architectcoders.usecases.GetPopularMoviesUseCase
import com.devexperto.architectcoders.usecases.RequestPopularMoviesUseCase
import com.devexperto.architectcoders.usecases.SwitchMovieFavoriteUseCase
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.core.module.dsl.factoryOf
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module

fun Application.initDI() {
    startKoin {
        androidLogger(Level.ERROR)
        androidContext(this@initDI)
        modules(appModule, dataModule, useCasesModule)
    }
}

private val appModule = module {
    single(named("apiKey")) { androidApplication().getString(R.string.api_key) }
    single {
        Room.databaseBuilder(
            get(),
            MovieDatabase::class.java, "movie-db"
        ).build()
    }
    single { get<MovieDatabase>().movieDao() }

    factoryOf(::MovieRoomDataSource) bind MovieLocalDataSource::class
    factory<MovieRemoteDataSource> { MovieServerDataSource(get(named("apiKey"))) }
    factoryOf(::PlayServicesLocationDataSource) bind LocationDataSource::class
    factoryOf(::AndroidPermissionChecker) bind PermissionChecker::class

    viewModelOf(::MainViewModel)
    viewModelOf(::DetailViewModel)
}

private val dataModule = module {
    factoryOf(::RegionRepository)
    factoryOf(::MoviesRepository)
}

private val useCasesModule = module {
    factoryOf(::GetPopularMoviesUseCase)
    factoryOf(::RequestPopularMoviesUseCase)
    factoryOf(::FindMovieUseCase)
    factoryOf(::SwitchMovieFavoriteUseCase)
}