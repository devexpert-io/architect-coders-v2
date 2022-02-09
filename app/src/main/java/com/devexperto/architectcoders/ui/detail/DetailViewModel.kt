package com.devexperto.architectcoders.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.devexperto.architectcoders.model.Movie

class DetailViewModel(movie: Movie) : ViewModel() {

    class UiState(val movie: Movie)

    private val _state = MutableLiveData(UiState(movie))
    val state: LiveData<UiState> get() = _state
}


@Suppress("UNCHECKED_CAST")
class DetailViewModelFactory(private val movie: Movie) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DetailViewModel(movie) as T
    }
}