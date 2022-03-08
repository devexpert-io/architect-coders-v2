package com.devexperto.architectcoders.data.datasource

import arrow.core.Either
import com.devexperto.architectcoders.domain.Error
import com.devexperto.architectcoders.domain.Movie

interface MovieRemoteDataSource {
    suspend fun findPopularMovies(region: String): Either<Error, List<Movie>>
}