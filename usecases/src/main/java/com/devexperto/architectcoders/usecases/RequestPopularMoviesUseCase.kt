package com.devexperto.architectcoders.usecases

import com.devexperto.architectcoders.domain.Error
import com.devexperto.architectcoders.data.MoviesRepository
import org.koin.core.annotation.Factory

@Factory
class RequestPopularMoviesUseCase(private val moviesRepository: MoviesRepository) {

    suspend operator fun invoke(): Error? {
        return moviesRepository.requestPopularMovies()
    }
}