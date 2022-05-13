package com.devexperto.architectcoders.data

import android.annotation.SuppressLint
import android.app.Application
import android.location.Geocoder
import android.location.Location
import arrow.core.Option
import com.devexperto.architectcoders.data.datasource.LocationDataSource
import com.google.android.gms.location.LocationServices
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class PlayServicesLocationDataSource @Inject constructor(application: Application) :
    LocationDataSource {
    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(application)
    private val geocoder = Geocoder(application)

    @SuppressLint("MissingPermission")
    override fun findLastRegion(): Single<Option<String>> {
        return Single.create { emitter ->
            fusedLocationClient.lastLocation
                .addOnCompleteListener { location ->
                    emitter.onSuccess(Option.fromNullable(location.result.toRegion()))
                }
        }
    }

    private fun Location?.toRegion(): String? {
        val addresses = this?.let {
            geocoder.getFromLocation(latitude, longitude, 1)
        }
        return addresses?.firstOrNull()?.countryCode
    }
}
