package com.devexperto.architectcoders.ui.detail

import app.cash.turbine.test
import com.devexperto.architectcoders.data.server.RemoteMovie
import com.devexperto.architectcoders.testrules.CoroutinesTestRule
import com.devexperto.architectcoders.ui.buildDatabaseMovies
import com.devexperto.architectcoders.ui.buildRepositoryWith
import com.devexperto.architectcoders.ui.detail.DetailViewModel.UiState
import com.devexperto.architectcoders.usecases.FindMovieUseCase
import com.devexperto.architectcoders.usecases.SwitchMovieFavoriteUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import com.devexperto.architectcoders.data.database.Movie as DatabaseMovie

@ExperimentalCoroutinesApi
class DetailIntegrationTests {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @Test
    fun `UI is updated with the movie on start`() = runTest {
        val vm = buildViewModelWith(
            id = 2,
            localData = buildDatabaseMovies(1, 2, 3)
        )

        vm.state.test {
            Assert.assertEquals(UiState(), awaitItem())
            Assert.assertEquals(2, awaitItem().movie!!.id)
            cancel()
        }
    }

    @Test
    fun `Favorite is updated in local data source`() = runTest {
        val vm = buildViewModelWith(
            id = 2,
            localData = buildDatabaseMovies(1, 2, 3)
        )

        vm.onFavoriteClicked()

        vm.state.test {
            Assert.assertEquals(UiState(), awaitItem())
            Assert.assertEquals(false, awaitItem().movie!!.favorite)
            Assert.assertEquals(true, awaitItem().movie!!.favorite)
            cancel()
        }
    }

    private fun buildViewModelWith(
        id: Int,
        localData: List<DatabaseMovie> = emptyList(),
        remoteData: List<RemoteMovie> = emptyList()
    ): DetailViewModel {
        val moviesRepository = buildRepositoryWith(localData, remoteData)
        val findMovieUseCase = FindMovieUseCase(moviesRepository)
        val switchMovieFavoriteUseCase = SwitchMovieFavoriteUseCase(moviesRepository)
        return DetailViewModel(id, findMovieUseCase, switchMovieFavoriteUseCase)
    }
}