package com.devexperto.architectcoders

import arrow.core.Option
import com.devexperto.architectcoders.data.PermissionChecker
import com.devexperto.architectcoders.data.database.MovieDao
import com.devexperto.architectcoders.data.datasource.LocationDataSource
import com.devexperto.architectcoders.data.server.RemoteMovie
import com.devexperto.architectcoders.data.server.RemoteResult
import com.devexperto.architectcoders.data.server.RemoteService
import io.reactivex.rxjava3.core.BackpressureStrategy
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.subjects.BehaviorSubject
import com.devexperto.architectcoders.data.database.Movie as DatabaseMovie

class FakeMovieDao(movies: List<DatabaseMovie> = emptyList()) : MovieDao {

    private val inMemoryMovies =
        BehaviorSubject.create<List<DatabaseMovie>>().apply { onNext(movies) }
    private val findMovieSubject = BehaviorSubject.create<DatabaseMovie>()

    override fun getAll(): Flowable<List<DatabaseMovie>> =
        Flowable.fromObservable(inMemoryMovies, BackpressureStrategy.BUFFER)

    override fun findById(id: Int): Flowable<DatabaseMovie> {
        findMovieSubject.onNext(inMemoryMovies.value?.first { it.id == id })
        return Flowable.fromObservable(findMovieSubject, BackpressureStrategy.BUFFER)
    }

    override fun movieCount(): Single<Int> = Single.just(inMemoryMovies.value?.size ?: 0)

    override fun insertMovies(movies: List<DatabaseMovie>): Completable {
        inMemoryMovies.onNext(movies)

        movies.firstOrNull() { it.id == findMovieSubject.value?.id }
            ?.let { findMovieSubject.onNext(it) }

        return Completable.complete()
    }

}

class FakeRemoteService(private val movies: List<RemoteMovie> = emptyList()) : RemoteService {

    override fun listPopularMovies(apiKey: String, region: String): Single<RemoteResult> =
        Single.just(
            RemoteResult(
                1,
                movies,
                1,
                movies.size
            )
        )

}

class FakeLocationDataSource : LocationDataSource {
    var location = "US"

    override fun findLastRegion(): Single<Option<String>> = Single.just(Option(location))
}

class FakePermissionChecker : PermissionChecker {
    var permissionGranted = true

    override fun check(permission: PermissionChecker.Permission) = permissionGranted
}