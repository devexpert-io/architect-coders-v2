package com.devexperto.architectcoders.di

import com.devexperto.architectcoders.data.MoviesRepository
import com.devexperto.architectcoders.usecases.FindMovieUseCase
import com.devexperto.architectcoders.usecases.GetPopularMoviesUseCase
import com.devexperto.architectcoders.usecases.RequestPopularMoviesUseCase
import com.devexperto.architectcoders.usecases.SwitchMovieFavoriteUseCase
import dagger.Module
import dagger.Provides

@Module
object UseCaseModule {

    @Provides
    fun provideGetPopularMoviesUseCase(moviesRepository: MoviesRepository) =
        GetPopularMoviesUseCase(moviesRepository)

    @Provides
    fun provideRequestPopularMoviesUseCase(moviesRepository: MoviesRepository) =
        RequestPopularMoviesUseCase(moviesRepository)

    @Provides
    fun provideFindMovieUseCase(moviesRepository: MoviesRepository) = FindMovieUseCase(moviesRepository)

    @Provides
    fun provideSwitchMovieFavoriteUseCase(moviesRepository: MoviesRepository) =
        SwitchMovieFavoriteUseCase(moviesRepository)
}