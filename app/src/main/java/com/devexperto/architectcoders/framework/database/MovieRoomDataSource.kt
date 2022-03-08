package com.devexperto.architectcoders.framework.database

import com.devexperto.architectcoders.data.datasource.MovieLocalDataSource
import com.devexperto.architectcoders.domain.Movie
import com.devexperto.architectcoders.framework.database.MovieDao
import com.devexperto.architectcoders.framework.database.Movie as DbMovie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MovieRoomDataSource(private val movieDao: MovieDao) : MovieLocalDataSource {

    override val movies: Flow<List<Movie>> = movieDao.getAll().map { it.toDomainModel() }

    override suspend fun isEmpty(): Boolean = movieDao.movieCount() == 0

    override fun findById(id: Int): Flow<Movie> = movieDao.findById(id).map { it.toDomainModel() }

    override suspend fun save(movies: List<Movie>) {
        movieDao.insertMovies(movies.fromDomainModel())
    }
}

private fun List<DbMovie>.toDomainModel(): List<Movie> = map { it.toDomainModel() }

private fun DbMovie.toDomainModel(): Movie = Movie(
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