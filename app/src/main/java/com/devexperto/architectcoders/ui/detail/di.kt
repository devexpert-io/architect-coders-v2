package com.devexperto.architectcoders.ui.detail

import androidx.lifecycle.SavedStateHandle
import com.devexperto.architectcoders.composeui.screens.NavArgs
import com.devexperto.architectcoders.di.MovieId
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
class DetailViewModelModule {

    @Provides
    @ViewModelScoped
    @MovieId
    fun provideMovieId(savedStateHandle: SavedStateHandle): Int {

        // Code for regular navigation
        val movieId = DetailFragmentArgs.fromSavedStateHandle(savedStateHandle).movieId
        if (movieId > -1) return movieId

        // Code for Jetpack Compose navigation
        return savedStateHandle[NavArgs.ItemId.key] ?: -1
    }

}