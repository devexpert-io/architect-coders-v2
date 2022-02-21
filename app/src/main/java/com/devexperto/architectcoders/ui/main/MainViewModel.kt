package com.devexperto.architectcoders.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.devexperto.architectcoders.model.MoviesRepository
import com.devexperto.architectcoders.model.database.Movie
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MainViewModel(private val moviesRepository: MoviesRepository) : ViewModel() {

    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            moviesRepository.popularMovies.collect { movies ->
                _state.value = UiState(movies = movies)
            }
        }
    }

    fun onUiReady() {
        viewModelScope.launch {
            _state.value = UiState(loading = true)
            moviesRepository.requestPopularMovies()
        }
    }

    data class UiState(
        val loading: Boolean = false,
        val movies: List<Movie>? = null,
    )
}

@Suppress("UNCHECKED_CAST")
class MainViewModelFactory(private val moviesRepository: MoviesRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(moviesRepository) as T
    }
}