package com.devexperto.architectcoders.data.database

import com.devexperto.architectcoders.data.datasource.MovieLocalDataSource
import com.devexperto.architectcoders.domain.Movie
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject
import com.devexperto.architectcoders.data.database.Movie as DbMovie

class MovieRoomDataSource @Inject constructor(private val movieDao: MovieDao) :
    MovieLocalDataSource {

    override val movies: Flowable<List<Movie>> = movieDao.getAll().map { it.toDomainModel() }

    override fun isEmpty(): Single<Boolean> = movieDao.movieCount().map { it <= 0 }

    override fun findById(id: Int): Flowable<Movie> =
        movieDao.findById(id).map { it.toDomainModel() }

    override fun save(movies: List<Movie>): Completable =
        movieDao.insertMovies(movies.fromDomainModel())
}

private fun List<DbMovie>.toDomainModel(): List<Movie> = map { it.toDomainModel() }

private fun DbMovie.toDomainModel(): Movie =
    Movie(
        id,
        title,
        overview,
        releaseDate,
        posterPath,
        backdropPath,
        originalLanguage,
        originalTitle,
        popularity,
        voteAverage,
        favorite
    )

private fun List<Movie>.fromDomainModel(): List<DbMovie> = map { it.fromDomainModel() }

private fun Movie.fromDomainModel(): DbMovie = DbMovie(
    id,
    title,
    overview,
    releaseDate,
    posterPath,
    backdropPath,
    originalLanguage,
    originalTitle,
    popularity,
    voteAverage,
    favorite
)