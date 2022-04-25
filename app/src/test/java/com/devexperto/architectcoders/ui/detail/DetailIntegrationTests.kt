package com.devexperto.architectcoders.ui.detail

import app.cash.turbine.test
import com.devexperto.architectcoders.domain.Movie
import com.devexperto.architectcoders.testrules.CoroutinesTestRule
import com.devexperto.architectcoders.testshared.sampleMovie
import com.devexperto.architectcoders.ui.buildRepositoryWith
import com.devexperto.architectcoders.ui.detail.DetailViewModel.UiState
import com.devexperto.architectcoders.usecases.FindMovieUseCase
import com.devexperto.architectcoders.usecases.SwitchMovieFavoriteUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class DetailIntegrationTests {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @Test
    fun `UI is updated with the movie on start`() = runTest {
        val vm = buildViewModelWith(
            id = 2,
            localData = listOf(sampleMovie.copy(1), sampleMovie.copy(2))
        )

        vm.state.test {
            Assert.assertEquals(UiState(), awaitItem())
            Assert.assertEquals(UiState(movie = sampleMovie.copy(2)), awaitItem())
            cancel()
        }
    }

    @Test
    fun `Favorite is updated in local data source`() = runTest {
        val vm = buildViewModelWith(
            id = 2,
            localData = listOf(sampleMovie.copy(1), sampleMovie.copy(2))
        )

        vm.onFavoriteClicked()

        vm.state.test {
            Assert.assertEquals(UiState(), awaitItem())
            Assert.assertEquals(
                UiState(movie = sampleMovie.copy(id = 2, favorite = false)),
                awaitItem()
            )
            Assert.assertEquals(
                UiState(movie = sampleMovie.copy(id = 2, favorite = true)),
                awaitItem()
            )
            cancel()
        }
    }

    private fun buildViewModelWith(
        id: Int,
        localData: List<Movie> = emptyList(),
        remoteData: List<Movie> = emptyList()
    ): DetailViewModel {
        val moviesRepository = buildRepositoryWith(localData, remoteData)
        val findMovieUseCase = FindMovieUseCase(moviesRepository)
        val switchMovieFavoriteUseCase = SwitchMovieFavoriteUseCase(moviesRepository)
        return DetailViewModel(id, findMovieUseCase, switchMovieFavoriteUseCase)
    }
}