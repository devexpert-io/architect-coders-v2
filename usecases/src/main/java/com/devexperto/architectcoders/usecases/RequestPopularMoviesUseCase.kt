package com.devexperto.architectcoders.usecases

import com.devexperto.architectcoders.data.MoviesRepository
import io.reactivex.rxjava3.core.Completable
import javax.inject.Inject

class RequestPopularMoviesUseCase @Inject constructor(private val moviesRepository: MoviesRepository) {

    operator fun invoke(): Completable = moviesRepository.requestPopularMovies()
}