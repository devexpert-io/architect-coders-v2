package com.devexperto.architectcoders.data.datasource

import arrow.core.Option
import io.reactivex.rxjava3.core.Single

interface LocationDataSource {
    fun findLastRegion(): Single<Option<String>>
}