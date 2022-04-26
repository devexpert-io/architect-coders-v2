package com.devexperto.architectcoders

import com.devexperto.architectcoders.data.PermissionChecker
import com.devexperto.architectcoders.data.database.MovieDao
import com.devexperto.architectcoders.data.datasource.LocationDataSource
import com.devexperto.architectcoders.data.server.RemoteMovie
import com.devexperto.architectcoders.data.server.RemoteResult
import com.devexperto.architectcoders.data.server.RemoteService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import com.devexperto.architectcoders.data.database.Movie as DatabaseMovie

class FakeMovieDao(movies: List<DatabaseMovie> = emptyList()) : MovieDao {

    private val inMemoryMovies = MutableStateFlow(movies)
    private lateinit var findMovieFlow: MutableStateFlow<DatabaseMovie>

    override fun getAll(): Flow<List<DatabaseMovie>> = inMemoryMovies

    override fun findById(id: Int): Flow<DatabaseMovie> {
        findMovieFlow = MutableStateFlow(inMemoryMovies.value.first { it.id == id })
        return findMovieFlow
    }

    override suspend fun movieCount(): Int = inMemoryMovies.value.size

    override suspend fun insertMovies(movies: List<DatabaseMovie>) {
        inMemoryMovies.value = movies

        if (::findMovieFlow.isInitialized) {
            movies.firstOrNull() { it.id == findMovieFlow.value.id }
                ?.let { findMovieFlow.value = it }
        }

    }

}

class FakeRemoteService(private val movies: List<RemoteMovie> = emptyList()) : RemoteService {

    override suspend fun listPopularMovies(apiKey: String, region: String) = RemoteResult(
        1,
        movies,
        1,
        movies.size
    )

}

class FakeLocationDataSource : LocationDataSource {
    var location = "US"

    override suspend fun findLastRegion(): String = location
}

class FakePermissionChecker : PermissionChecker {
    var permissionGranted = true

    override fun check(permission: PermissionChecker.Permission) = permissionGranted
}