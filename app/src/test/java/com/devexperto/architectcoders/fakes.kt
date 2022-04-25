package com.devexperto.architectcoders

import arrow.core.right
import com.devexperto.architectcoders.data.PermissionChecker
import com.devexperto.architectcoders.data.datasource.LocationDataSource
import com.devexperto.architectcoders.data.datasource.MovieLocalDataSource
import com.devexperto.architectcoders.data.datasource.MovieRemoteDataSource
import com.devexperto.architectcoders.domain.Error
import com.devexperto.architectcoders.domain.Movie
import com.devexperto.architectcoders.testshared.sampleMovie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf

val defaultFakeMovies = listOf(
    sampleMovie.copy(1),
    sampleMovie.copy(2),
    sampleMovie.copy(3),
    sampleMovie.copy(4)
)

class FakeLocalDataSource : MovieLocalDataSource {

    val inMemoryMovies = MutableStateFlow<List<Movie>>(emptyList())

    override val movies = inMemoryMovies

    override suspend fun isEmpty() = movies.value.isEmpty()

    override fun findById(id: Int): Flow<Movie> = flowOf(inMemoryMovies.value.first { it.id == id })

    override suspend fun save(movies: List<Movie>): Error? {
        inMemoryMovies.value = movies
        return null
    }
}

class FakeRemoteDataSource : MovieRemoteDataSource {

    var movies = defaultFakeMovies

    override suspend fun findPopularMovies(region: String) = movies.right()
}

class FakeLocationDataSource : LocationDataSource {
    var location = "US"

    override suspend fun findLastRegion(): String = location
}

class FakePermissionChecker : PermissionChecker {
    var permissionGranted = true

    override fun check(permission: PermissionChecker.Permission) = permissionGranted
}