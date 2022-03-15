package com.devexperto.architectcoders.usecases

import com.devexperto.architectcoders.data.MoviesRepository
import com.devexperto.architectcoders.domain.Movie
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Factory

@Factory
class FindMovieUseCase(private val repository: MoviesRepository) {

    operator fun invoke(id: Int): Flow<Movie> = repository.findById(id)
}