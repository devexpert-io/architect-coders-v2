package com.devexperto.architectcoders.usecases

import com.devexperto.architectcoders.data.MoviesRepository
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class RequestPopularMoviesUseCaseTest {

    @Test
    fun `Invoke calls movies repository`() {
        val moviesRepository = mock<MoviesRepository>()
        val requestPopularMoviesUseCase = RequestPopularMoviesUseCase(moviesRepository)

        requestPopularMoviesUseCase()

        verify(moviesRepository).requestPopularMovies()
    }
}