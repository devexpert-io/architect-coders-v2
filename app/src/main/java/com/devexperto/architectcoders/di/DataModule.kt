package com.devexperto.architectcoders.di

import com.devexperto.architectcoders.data.MoviesRepository
import com.devexperto.architectcoders.data.PermissionChecker
import com.devexperto.architectcoders.data.RegionRepository
import com.devexperto.architectcoders.data.datasource.LocationDataSource
import com.devexperto.architectcoders.data.datasource.MovieLocalDataSource
import com.devexperto.architectcoders.data.datasource.MovieRemoteDataSource
import dagger.Module
import dagger.Provides

@Module
object DataModule {

    @Provides
    fun provideRegionRepository(
        locationDataSource: LocationDataSource,
        permissionChecker: PermissionChecker
    ) = RegionRepository(locationDataSource, permissionChecker)

    @Provides
    fun provideMoviesRepository(
        localDataSource: MovieLocalDataSource,
        remoteDataSource: MovieRemoteDataSource,
        regionRepository: RegionRepository
    ) = MoviesRepository(regionRepository, localDataSource, remoteDataSource)
}