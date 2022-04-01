package com.devexperto.architectcoders.usecases

import com.devexperto.architectcoders.data.MoviesRepository
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class SwitchMovieFavoriteUseCaseTest {

    @Test
    fun `Invoke calls movies repository`(): Unit = runBlocking {
            val movie = sampleMovie.copy(id = 1)
            val moviesRepository = mock<MoviesRepository>()
            val switchMovieFavoriteUseCase = SwitchMovieFavoriteUseCase(moviesRepository)

            switchMovieFavoriteUseCase(movie)

            verify(moviesRepository).switchFavorite(movie)
        }
    }