package com.devexperto.architectcoders.data

import com.devexperto.architectcoders.data.datasource.MovieLocalDataSource
import com.devexperto.architectcoders.data.datasource.MovieRemoteDataSource
import com.devexperto.architectcoders.domain.Movie
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class MoviesRepository @Inject constructor(
    private val regionRepository: RegionRepository,
    private val localDataSource: MovieLocalDataSource,
    private val remoteDataSource: MovieRemoteDataSource
) {
    val popularMovies get() = localDataSource.movies

    fun findById(id: Int): Flowable<Movie> = localDataSource.findById(id)

    fun requestPopularMovies(): Completable = localDataSource.isEmpty()
        .filter { it }
        .flatMapSingle { regionRepository.findLastRegion() }
        .flatMapSingle { remoteDataSource.findPopularMovies(it) }
        .flatMapCompletable { localDataSource.save(it) }

    fun switchFavorite(movie: Movie): Completable = Single
        .just(movie.copy(favorite = !movie.favorite))
        .flatMapCompletable { localDataSource.save(listOf(it)) }
}