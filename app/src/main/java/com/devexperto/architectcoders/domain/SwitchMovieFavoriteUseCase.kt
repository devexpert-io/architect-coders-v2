package com.devexperto.architectcoders.domain

import com.devexperto.architectcoders.data.MoviesRepository
import com.devexperto.architectcoders.data.database.Movie

class SwitchMovieFavoriteUseCase(private val repository: MoviesRepository) {

    suspend operator fun invoke(movie: Movie) = repository.switchFavorite(movie)
}