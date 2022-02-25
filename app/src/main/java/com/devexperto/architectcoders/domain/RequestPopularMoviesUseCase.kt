package com.devexperto.architectcoders.domain

import com.devexperto.architectcoders.data.Error
import com.devexperto.architectcoders.data.MoviesRepository

class RequestPopularMoviesUseCase(private val moviesRepository: MoviesRepository) {

    suspend operator fun invoke(): Error? {
        return moviesRepository.requestPopularMovies()
    }
}