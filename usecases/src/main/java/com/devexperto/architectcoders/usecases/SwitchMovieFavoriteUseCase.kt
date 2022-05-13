package com.devexperto.architectcoders.usecases

import com.devexperto.architectcoders.data.MoviesRepository
import com.devexperto.architectcoders.domain.Movie
import io.reactivex.rxjava3.core.Completable
import javax.inject.Inject

class SwitchMovieFavoriteUseCase @Inject constructor(private val repository: MoviesRepository) {

    operator fun invoke(movie: Movie): Completable = repository.switchFavorite(movie)
}