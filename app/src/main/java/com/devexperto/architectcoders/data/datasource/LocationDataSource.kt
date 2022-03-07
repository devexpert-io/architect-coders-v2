package com.devexperto.architectcoders.data.datasource

interface LocationDataSource {
    suspend fun findLastRegion(): String?
}