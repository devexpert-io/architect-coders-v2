package com.devexperto.architectcoders.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.devexperto.architectcoders.SchedulerProvider
import com.devexperto.architectcoders.di.MovieId
import com.devexperto.architectcoders.domain.Error
import com.devexperto.architectcoders.domain.Movie
import com.devexperto.architectcoders.usecases.FindMovieUseCase
import com.devexperto.architectcoders.usecases.SwitchMovieFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable
import javax.inject.Inject


@HiltViewModel
class DetailViewModel @Inject constructor(
    @MovieId private val movieId: Int,
    findMovieUseCase: FindMovieUseCase,
    private val switchMovieFavoriteUseCase: SwitchMovieFavoriteUseCase,
    private val schedulerProvider: SchedulerProvider
) : ViewModel() {

    private val _state = MutableLiveData(UiState())
    val state: LiveData<UiState> get() = _state

    private val disposable = CompositeDisposable()

    init {
        disposable.add(
            findMovieUseCase(movieId)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe { _state.value = UiState(movie = it) }
        )
    }

    fun onFavoriteClicked() {
        _state.value?.movie?.let {
            disposable.add(
                switchMovieFavoriteUseCase(it)
                    .subscribeOn(schedulerProvider.io())
                    .observeOn(schedulerProvider.ui())
                    .subscribe()
            )
        }
    }

    override fun onCleared() {
        disposable.dispose()
        super.onCleared()
    }

    data class UiState(val movie: Movie? = null, val error: Error? = null)
}