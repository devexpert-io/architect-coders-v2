package com.devexperto.architectcoders.domain

import com.devexperto.architectcoders.data.MoviesRepository

class GetPopularMoviesUseCase(private val repository: MoviesRepository) {

    operator fun invoke() = repository.popularMovies
}