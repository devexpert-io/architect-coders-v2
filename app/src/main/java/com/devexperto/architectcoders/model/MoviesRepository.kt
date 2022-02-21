package com.devexperto.architectcoders.model

import com.devexperto.architectcoders.App
import com.devexperto.architectcoders.R
import com.devexperto.architectcoders.model.database.Movie
import com.devexperto.architectcoders.model.datasource.MovieLocalDataSource
import com.devexperto.architectcoders.model.datasource.MovieRemoteDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MoviesRepository(application: App) {

    private val localDataSource = MovieLocalDataSource(application.db.movieDao())
    private val remoteDataSource = MovieRemoteDataSource(
        application.getString(R.string.api_key),
        regionRepository = RegionRepository(application)
    )

    val popularMovies = localDataSource.movies

    fun findById(id: Int) = localDataSource.findById(id)

    suspend fun requestPopularMovies() = withContext(Dispatchers.IO) {
        if (localDataSource.isEmpty()) {
            val movies = remoteDataSource.findPopularMovies()
            localDataSource.save(movies.results.toLocalModel())
        }
    }
}

private fun List<RemoteMovie>.toLocalModel(): List<Movie> = map { it.toLocalModel() }

private fun RemoteMovie.toLocalModel(): Movie = Movie(
    id,
    title,
    overview,
    releaseDate,
    posterPath,
    backdropPath ?: "",
    originalLanguage,
    originalTitle,
    popularity,
    voteAverage
)