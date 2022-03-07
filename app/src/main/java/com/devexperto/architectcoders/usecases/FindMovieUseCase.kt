package com.devexperto.architectcoders.usecases

import com.devexperto.architectcoders.data.MoviesRepository

class FindMovieUseCase(private val repository: MoviesRepository) {

    operator fun invoke(id: Int) = repository.findById(id)
}