package com.devexperto.architectcoders.model.datasource

import com.devexperto.architectcoders.model.RemoteConnection

class MovieRemoteDataSource(private val apiKey: String) {

    suspend fun findPopularMovies(region: String) =
        RemoteConnection.service.listPopularMovies(apiKey, region)
}