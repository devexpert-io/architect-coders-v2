package com.devexperto.architectcoders.data.datasource

import com.devexperto.architectcoders.data.RemoteConnection

class MovieRemoteDataSource(private val apiKey: String) {

    suspend fun findPopularMovies(region: String) =
        RemoteConnection.service.listPopularMovies(apiKey, region)
}