package com.devexperto.architectcoders.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.devexperto.architectcoders.SchedulerProvider
import com.devexperto.architectcoders.domain.Error
import com.devexperto.architectcoders.domain.Movie
import com.devexperto.architectcoders.usecases.GetPopularMoviesUseCase
import com.devexperto.architectcoders.usecases.RequestPopularMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    getPopularMoviesUseCase: GetPopularMoviesUseCase,
    private val requestPopularMoviesUseCase: RequestPopularMoviesUseCase,
    private val schedulerProvider: SchedulerProvider
) : ViewModel() {

    private val disposable = CompositeDisposable()

    private val _state = MutableLiveData(UiState())
    val state: LiveData<UiState> get() = _state

    init {
        disposable.add(
            getPopularMoviesUseCase()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe { movies -> _state.value = UiState(movies = movies) }
        )
    }

    fun onUiReady() {
        disposable.add(
            requestPopularMoviesUseCase()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .doOnSubscribe { _state.value = _state.value?.copy(loading = true) }
                .doOnComplete { _state.value = _state.value?.copy(loading = false) }
                .subscribe()
        )
    }

    override fun onCleared() {
        disposable.dispose()
        super.onCleared()
    }

    data class UiState(
        val loading: Boolean = false,
        val movies: List<Movie>? = null,
        val error: Error? = null
    )
}