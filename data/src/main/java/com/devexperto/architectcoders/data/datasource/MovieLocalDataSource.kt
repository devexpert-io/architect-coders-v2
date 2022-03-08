package com.devexperto.architectcoders.data.datasource

import com.devexperto.architectcoders.domain.Error
import com.devexperto.architectcoders.domain.Movie
import kotlinx.coroutines.flow.Flow

interface MovieLocalDataSource {
    val movies: Flow<List<Movie>>

    suspend fun isEmpty(): Boolean
    fun findById(id: Int): Flow<Movie>
    suspend fun save(movies: List<Movie>): Error?
}