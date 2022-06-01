package com.devexperto.architectcoders.usecases

import com.devexperto.architectcoders.testshared.sampleMovie
import io.reactivex.rxjava3.core.Flowable
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock

class GetPopularMoviesUseCaseTest {

    @Test
    fun `Invoke calls movies repository`() {
        val movies = listOf(sampleMovie.copy(id = 1))
        val getPopularMoviesUseCase = GetPopularMoviesUseCase(mock {
            on { popularMovies } doReturn Flowable.just(movies)
        })

        val result = getPopularMoviesUseCase()

        result.test().assertResult(movies)
    }
}