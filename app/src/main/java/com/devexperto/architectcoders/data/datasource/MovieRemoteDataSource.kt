package com.devexperto.architectcoders.data.datasource

import com.devexperto.architectcoders.domain.Movie

interface MovieRemoteDataSource {
    suspend fun findPopularMovies(region: String): List<Movie>
}