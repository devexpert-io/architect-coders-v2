package com.devexperto.architectcoders.usecases

import com.devexperto.architectcoders.data.MoviesRepository
import com.devexperto.architectcoders.testshared.sampleMovie
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class SwitchMovieFavoriteUseCaseTest {

    @Test
    fun `Invoke calls movies repository`() {
            val movie = sampleMovie.copy(id = 1)
            val moviesRepository = mock<MoviesRepository>()
            val switchMovieFavoriteUseCase = SwitchMovieFavoriteUseCase(moviesRepository)

            switchMovieFavoriteUseCase(movie)

            verify(moviesRepository).switchFavorite(movie)
        }
    }