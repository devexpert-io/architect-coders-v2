package com.devexperto.architectcoders.data

import com.devexperto.architectcoders.data.PermissionChecker.Permission.COARSE_LOCATION
import com.devexperto.architectcoders.data.datasource.LocationDataSource
import org.koin.core.annotation.Factory

@Factory
class RegionRepository(
    private val locationDataSource: LocationDataSource,
    private val permissionChecker: PermissionChecker
) {

    companion object {
        private const val DEFAULT_REGION = "US"
    }

    suspend fun findLastRegion(): String {
        return if (permissionChecker.check(COARSE_LOCATION)) {
            locationDataSource.findLastRegion() ?: DEFAULT_REGION
        } else {
            DEFAULT_REGION
        }
    }
}