package com.devexperto.architectcoders.usecases

import com.devexperto.architectcoders.data.MoviesRepository
import javax.inject.Inject

class GetPopularMoviesUseCase @Inject constructor(private val repository: MoviesRepository) {

    operator fun invoke() = repository.popularMovies
}