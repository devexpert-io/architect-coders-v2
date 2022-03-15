package com.devexperto.architectcoders.usecases

import com.devexperto.architectcoders.data.MoviesRepository
import com.devexperto.architectcoders.domain.Movie
import org.koin.core.annotation.Factory

@Factory
class SwitchMovieFavoriteUseCase(private val repository: MoviesRepository) {

    suspend operator fun invoke(movie: Movie) = repository.switchFavorite(movie)
}