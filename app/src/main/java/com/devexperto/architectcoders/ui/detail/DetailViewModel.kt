package com.devexperto.architectcoders.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.devexperto.architectcoders.model.database.Movie
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class DetailViewModel(movie: Movie) : ViewModel() {

    class UiState(val movie: Movie)

    private val _state = MutableStateFlow(UiState(movie))
    val state: StateFlow<UiState> = _state.asStateFlow()
}

@Suppress("UNCHECKED_CAST")
class DetailViewModelFactory(private val movie: Movie) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DetailViewModel(movie) as T
    }
}