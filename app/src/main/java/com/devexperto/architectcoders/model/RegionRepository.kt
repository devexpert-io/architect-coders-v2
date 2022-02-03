package com.devexperto.architectcoders.model

import android.Manifest
import android.location.Geocoder
import android.location.Location
import androidx.appcompat.app.AppCompatActivity

class RegionRepository(activity: AppCompatActivity) {

    companion object {
        private const val DEFAULT_REGION = "US"
    }

    private val locationDataSource: LocationDataSource = PlayServicesLocationDataSource(activity)
    private val coarsePermissionChecker = PermissionChecker(
        activity,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )
    private val geocoder = Geocoder(activity)

    suspend fun findLastRegion(): String = findLastLocation().toRegion()

    private suspend fun findLastLocation(): Location? {
        val success = coarsePermissionChecker.request()
        return if (success) locationDataSource.findLastLocation() else null
    }

    private fun Location?.toRegion(): String {
        val addresses = this?.let {
            geocoder.getFromLocation(latitude, longitude, 1)
        }
        return addresses?.firstOrNull()?.countryCode ?: DEFAULT_REGION
    }
}