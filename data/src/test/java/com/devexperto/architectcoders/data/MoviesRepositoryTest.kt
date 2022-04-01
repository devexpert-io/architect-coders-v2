package com.devexperto.architectcoders.data

import arrow.core.right
import com.devexperto.architectcoders.data.datasource.MovieLocalDataSource
import com.devexperto.architectcoders.data.datasource.MovieRemoteDataSource
import com.devexperto.architectcoders.domain.Movie
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
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

    private val localMovies = flowOf(listOf(sampleMovie.copy(1)))

    @Before
    fun setUp() {
        whenever(localDataSource.movies).thenReturn(localMovies)
        moviesRepository = MoviesRepository(regionRepository, localDataSource, remoteDataSource)
    }

    @Test
    fun `Popular movies are taken from local data source if available`(): Unit = runBlocking {

        val result = moviesRepository.popularMovies

        assertEquals(localMovies, result)
    }

    @Test
    fun `Popular movies are saved to local data source when it's empty`(): Unit = runBlocking {
        val remoteMovies = listOf(sampleMovie.copy(2))
        whenever(localDataSource.isEmpty()).thenReturn(true)
        whenever(regionRepository.findLastRegion()).thenReturn(RegionRepository.DEFAULT_REGION)
        whenever(remoteDataSource.findPopularMovies(any())).thenReturn(remoteMovies.right())

        moviesRepository.requestPopularMovies()

        verify(localDataSource).save(remoteMovies)
    }

    @Test
    fun `Finding a movie by id is done in local data source`(): Unit = runBlocking {
        val movie = flowOf(sampleMovie.copy(id = 5))
        whenever(localDataSource.findById(5)).thenReturn(movie)

        val result = moviesRepository.findById(5)

        assertEquals(movie, result)
    }

    @Test
    fun `Switching favorite updates local data source`(): Unit = runBlocking {
        val movie = sampleMovie.copy(id = 3)

        moviesRepository.switchFavorite(movie)

        verify(localDataSource).save(argThat { get(0).id == 3 })
    }

    @Test
    fun `Switching favorite marks as favorite an unfavorite movie`(): Unit = runBlocking {
        val movie = sampleMovie.copy(favorite = false)

        moviesRepository.switchFavorite(movie)

        verify(localDataSource).save(argThat { get(0).favorite })
    }

    @Test
    fun `Switching favorite marks as unfavorite a favorite movie`(): Unit = runBlocking {
        val movie = sampleMovie.copy(favorite = true)

        moviesRepository.switchFavorite(movie)

        verify(localDataSource).save(argThat { !get(0).favorite })
    }
}

private val sampleMovie = Movie(
    0,
    "Title",
    "Overview",
    "01/01/2025",
    "",
    "",
    "EN",
    "Title",
    5.0,
    5.1,
    false
)