package com.devexperto.architectcoders.di

import com.devexperto.architectcoders.ui.detail.DetailViewModelFactory
import com.devexperto.architectcoders.ui.main.MainViewModelFactory
import com.devexperto.architectcoders.usecases.FindMovieUseCase
import com.devexperto.architectcoders.usecases.GetPopularMoviesUseCase
import com.devexperto.architectcoders.usecases.RequestPopularMoviesUseCase
import com.devexperto.architectcoders.usecases.SwitchMovieFavoriteUseCase
import dagger.Module
import dagger.Provides

@Module
object ViewModelsModule {

    @Provides
    fun provideMainViewModelFactory(
        getPopularMoviesUseCase: GetPopularMoviesUseCase,
        requestPopularMoviesUseCase: RequestPopularMoviesUseCase
    ) = MainViewModelFactory(getPopularMoviesUseCase, requestPopularMoviesUseCase)

    @Provides
    fun provideDetailViewModel(
        findMovieById: FindMovieUseCase,
        toggleMovieFavorite: SwitchMovieFavoriteUseCase
    ): DetailViewModelFactory {
        // TODO the id needs to be provided. We'll see it with scoped graphs
        return DetailViewModelFactory(-1, findMovieById, toggleMovieFavorite)
    }
}