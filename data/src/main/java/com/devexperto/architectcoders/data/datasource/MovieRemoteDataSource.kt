package com.devexperto.architectcoders.data.datasource

import com.devexperto.architectcoders.domain.Movie
import io.reactivex.rxjava3.core.Single

interface MovieRemoteDataSource {
    fun findPopularMovies(region: String): Single<List<Movie>>
}