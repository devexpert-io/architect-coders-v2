package com.devexperto.architectcoders.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devexperto.architectcoders.data.toError
import com.devexperto.architectcoders.di.MovieId
import com.devexperto.architectcoders.domain.Error
import com.devexperto.architectcoders.domain.Movie
import com.devexperto.architectcoders.usecases.FindMovieUseCase
import com.devexperto.architectcoders.usecases.SwitchMovieFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class DetailViewModel @Inject constructor(
    @MovieId private val movieId: Int,
    findMovieUseCase: FindMovieUseCase,
    private val switchMovieFavoriteUseCase: SwitchMovieFavoriteUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            findMovieUseCase(movieId)
                .catch { cause -> _state.update { it.copy(error = cause.toError()) } }
                .collect { movie -> _state.update { UiState(movie = movie) } }
        }
    }

    fun onFavoriteClicked() {
        viewModelScope.launch {
            _state.value.movie?.let { movie ->
                val error = switchMovieFavoriteUseCase(movie)
                _state.update { it.copy(error = error) }
            }
        }
    }

    data class UiState(val movie: Movie? = null, val error: Error? = null)
}