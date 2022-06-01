package com.devexperto.architectcoders.data

import com.devexperto.architectcoders.data.datasource.MovieLocalDataSource
import com.devexperto.architectcoders.data.datasource.MovieRemoteDataSource
import com.devexperto.architectcoders.testshared.sampleMovie
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
import org.mockito.kotlin.argThat
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class MoviesRepositoryTest {

    @Mock
    lateinit var localDataSource: MovieLocalDataSource

    @Mock
    lateinit var remoteDataSource: MovieRemoteDataSource

    @Mock
    lateinit var regionRepository: RegionRepository

    private lateinit var moviesRepository: MoviesRepository

    private val localMovies = listOf(sampleMovie.copy(1))

    @Before
    fun setUp() {
        whenever(localDataSource.movies).thenReturn(Flowable.just(localMovies))
        moviesRepository = MoviesRepository(regionRepository, localDataSource, remoteDataSource)
    }

    @Test
    fun `Popular movies are taken from local data source if available`() {

        val result = moviesRepository.popularMovies

        result.test().assertResult(localMovies)
    }

    @Test
    fun `Popular movies are saved to local data source when it's empty`() {
        val remoteMovies = listOf(sampleMovie.copy(2))
        whenever(localDataSource.isEmpty()).thenReturn(Single.just(true))
        whenever(regionRepository.findLastRegion()).thenReturn(Single.just(RegionRepository.DEFAULT_REGION))
        whenever(remoteDataSource.findPopularMovies(any())).thenReturn(Single.just(remoteMovies))

        moviesRepository.requestPopularMovies().blockingSubscribe()

        verify(localDataSource).save(remoteMovies)
    }

    @Test
    fun `Finding a movie by id is done in local data source`() {
        val movie = sampleMovie.copy(id = 5)
        whenever(localDataSource.findById(5)).thenReturn(Flowable.just(movie))

        val result = moviesRepository.findById(5)

        result.test().assertResult(movie)
    }

    @Test
    fun `Switching favorite updates local data source`() {
        val movie = sampleMovie.copy(id = 3)

        moviesRepository.switchFavorite(movie).blockingSubscribe()

        verify(localDataSource).save(argThat { get(0).id == 3 })
    }

    @Test
    fun `Switching favorite marks as favorite an unfavorite movie`() {
        val movie = sampleMovie.copy(favorite = false)

        moviesRepository.switchFavorite(movie).blockingSubscribe()

        verify(localDataSource).save(argThat { get(0).favorite })
    }

    @Test
    fun `Switching favorite marks as unfavorite a favorite movie`() {
        val movie = sampleMovie.copy(favorite = true)

        moviesRepository.switchFavorite(movie).blockingSubscribe()

        verify(localDataSource).save(argThat { !get(0).favorite })
    }
}