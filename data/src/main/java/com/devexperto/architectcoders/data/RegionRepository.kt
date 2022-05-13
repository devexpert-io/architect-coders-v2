package com.devexperto.architectcoders.data

import arrow.core.getOrElse
import com.devexperto.architectcoders.data.PermissionChecker.Permission.COARSE_LOCATION
import com.devexperto.architectcoders.data.datasource.LocationDataSource
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class RegionRepository @Inject constructor(
    private val locationDataSource: LocationDataSource,
    private val permissionChecker: PermissionChecker
) {

    companion object {
        const val DEFAULT_REGION = "US"
    }

    fun findLastRegion(): Single<String> {
        return if (permissionChecker.check(COARSE_LOCATION)) {
            locationDataSource
                .findLastRegion()
                .map { it.getOrElse { DEFAULT_REGION } }
        } else {
            Single.just(DEFAULT_REGION)
        }
    }
}