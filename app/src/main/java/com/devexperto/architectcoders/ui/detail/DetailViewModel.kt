package com.devexperto.architectcoders.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.devexperto.architectcoders.di.MovieId
import com.devexperto.architectcoders.domain.Error
import com.devexperto.architectcoders.domain.Movie
import com.devexperto.architectcoders.usecases.FindMovieUseCase
import com.devexperto.architectcoders.usecases.SwitchMovieFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject


@HiltViewModel
class DetailViewModel @Inject constructor(
    @MovieId private val movieId: Int,
    findMovieUseCase: FindMovieUseCase,
    private val switchMovieFavoriteUseCase: SwitchMovieFavoriteUseCase
) : ViewModel() {

    private val _state = MutableLiveData(UiState())
    val state: LiveData<UiState> get() = _state

    private val disposable = CompositeDisposable()

    init {
        disposable.add(
            findMovieUseCase(movieId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { _state.value = UiState(movie = it) }
        )
    }

    fun onFavoriteClicked() {
        _state.value?.movie?.let {
            disposable.add(
                switchMovieFavoriteUseCase(it)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
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