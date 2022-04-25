package com.devexperto.architectcoders.ui

import com.devexperto.architectcoders.FakeLocalDataSource
import com.devexperto.architectcoders.FakeLocationDataSource
import com.devexperto.architectcoders.FakePermissionChecker
import com.devexperto.architectcoders.FakeRemoteDataSource
import com.devexperto.architectcoders.data.MoviesRepository
import com.devexperto.architectcoders.data.RegionRepository
import com.devexperto.architectcoders.domain.Movie

fun buildRepositoryWith(
    localData: List<Movie>,
    remoteData: List<Movie>
): MoviesRepository {
    val locationDataSource = FakeLocationDataSource()
    val permissionChecker = FakePermissionChecker()
    val regionRepository = RegionRepository(locationDataSource, permissionChecker)
    val localDataSource = FakeLocalDataSource().apply { inMemoryMovies.value = localData }
    val remoteDataSource = FakeRemoteDataSource().apply { movies = remoteData }
    return MoviesRepository(regionRepository, localDataSource, remoteDataSource)
}