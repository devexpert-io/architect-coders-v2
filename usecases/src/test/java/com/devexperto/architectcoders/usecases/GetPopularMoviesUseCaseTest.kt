package com.devexperto.architectcoders.usecases

import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock

class GetPopularMoviesUseCaseTest {

    @Test
    fun `Invoke calls movies repository`(): Unit = runBlocking {
        val movies = flowOf(listOf(sampleMovie.copy(id = 1)))
        val getPopularMoviesUseCase = GetPopularMoviesUseCase(mock {
            on { popularMovies } doReturn movies
        })

        val result = getPopularMoviesUseCase()

        Assert.assertEquals(movies, result)
    }
}