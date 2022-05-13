package com.devexperto.architectcoders.data.datasource

import com.devexperto.architectcoders.domain.Movie
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single

interface MovieLocalDataSource {
    val movies: Flowable<List<Movie>>

    fun isEmpty(): Single<Boolean>
    fun findById(id: Int): Flowable<Movie>
    fun save(movies: List<Movie>): Completable
}