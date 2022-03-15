package com.devexperto.architectcoders.usecases

import com.devexperto.architectcoders.data.MoviesRepository
import org.koin.core.annotation.Factory

@Factory
class GetPopularMoviesUseCase(private val repository: MoviesRepository) {

    operator fun invoke() = repository.popularMovies
}