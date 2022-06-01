package com.devexperto.architectcoders.usecases

import com.devexperto.architectcoders.testshared.sampleMovie
import io.reactivex.rxjava3.core.Flowable
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock

class FindMovieUseCaseTest {

    @Test
    fun `Invoke calls movies repository`() {
        val movie = sampleMovie.copy(id = 1)
        val findMovieUseCase = FindMovieUseCase(mock() {
            on { findById(1) } doReturn Flowable.just(movie)
        })

        val result = findMovieUseCase(1)

        result.test().assertResult(movie)
    }
}