package com.devexperto.architectcoders.data.server

import com.devexperto.architectcoders.data.datasource.MovieRemoteDataSource
import com.devexperto.architectcoders.di.ApiKey
import com.devexperto.architectcoders.domain.Movie
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class MovieServerDataSource @Inject constructor(
    @ApiKey private val apiKey: String,
    private val remoteService: RemoteService
) :
    MovieRemoteDataSource {

    override fun findPopularMovies(region: String): Single<List<Movie>> =
        remoteService
            .listPopularMovies(apiKey, region)
            .map { it.results.toDomainModel() }
}

private fun List<RemoteMovie>.toDomainModel(): List<Movie> = map { it.toDomainModel() }

private fun RemoteMovie.toDomainModel(): Movie =
    Movie(
        id,
        title,
        overview,
        releaseDate,
        "https://image.tmdb.org/t/p/w185/$posterPath",
        backdropPath?.let { "https://image.tmdb.org/t/p/w780/$it" } ?: "",
        originalLanguage,
        originalTitle,
        popularity,
        voteAverage,
        false
    )