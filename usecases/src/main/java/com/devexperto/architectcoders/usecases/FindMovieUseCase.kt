package com.devexperto.architectcoders.usecases

import com.devexperto.architectcoders.data.MoviesRepository
import com.devexperto.architectcoders.domain.Movie
import io.reactivex.rxjava3.core.Flowable
import javax.inject.Inject

class FindMovieUseCase @Inject constructor(private val repository: MoviesRepository) {

    operator fun invoke(id: Int): Flowable<Movie> = repository.findById(id)
}