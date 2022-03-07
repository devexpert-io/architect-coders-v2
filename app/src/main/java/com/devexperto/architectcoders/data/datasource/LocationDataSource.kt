package com.devexperto.architectcoders.data.datasource

import android.location.Location

interface LocationDataSource {
    suspend fun findLastLocation(): Location?
}