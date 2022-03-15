package com.devexperto.architectcoders.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devexperto.architectcoders.data.toError
import com.devexperto.architectcoders.domain.Error
import com.devexperto.architectcoders.domain.Movie
import com.devexperto.architectcoders.usecases.GetPopularMoviesUseCase
import com.devexperto.architectcoders.usecases.RequestPopularMoviesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class MainViewModel(
    getPopularMoviesUseCase: GetPopularMoviesUseCase,
    private val requestPopularMoviesUseCase: RequestPopularMoviesUseCase
) :
    ViewModel() {

    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            getPopularMoviesUseCase()
                .catch { cause -> _state.update { it.copy(error = cause.toError()) } }
                .collect { movies -> _state.update { UiState(movies = movies) } }
        }
    }

    fun onUiReady() {
        viewModelScope.launch {
            _state.value = _state.value.copy(loading = true)
            val error = requestPopularMoviesUseCase()
            _state.update { _state.value.copy(loading = false, error = error) }
        }
    }

    data class UiState(
        val loading: Boolean = false,
        val movies: List<Movie>? = null,
        val error: Error? = null
    )
}