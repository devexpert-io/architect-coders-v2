package com.devexperto.architectcoders.ui.main

import app.cash.turbine.test
import com.devexperto.architectcoders.domain.Movie
import com.devexperto.architectcoders.testrules.CoroutinesTestRule
import com.devexperto.architectcoders.testshared.sampleMovie
import com.devexperto.architectcoders.ui.buildRepositoryWith
import com.devexperto.architectcoders.ui.main.MainViewModel.UiState
import com.devexperto.architectcoders.usecases.GetPopularMoviesUseCase
import com.devexperto.architectcoders.usecases.RequestPopularMoviesUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class MainIntegrationTests {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @Test
    fun `data is loaded from server when local source is empty`() = runTest {
        val remoteData = listOf(sampleMovie.copy(1), sampleMovie.copy(2))
        val vm = buildViewModelWith(
            localData = emptyList(),
            remoteData = remoteData
        )

        vm.onUiReady()

        vm.state.test {
            Assert.assertEquals(UiState(), awaitItem())
            Assert.assertEquals(UiState(movies = emptyList()), awaitItem())
            Assert.assertEquals(UiState(movies = emptyList(), loading = true), awaitItem())
            Assert.assertEquals(UiState(movies = emptyList(), loading = false), awaitItem())
            Assert.assertEquals(UiState(movies = remoteData, loading = false), awaitItem())
            cancel()
        }
    }

    @Test
    fun `data is loaded from local source when available`() = runTest {
        val localData = listOf(sampleMovie.copy(10), sampleMovie.copy(11))
        val remoteData = listOf(sampleMovie.copy(1), sampleMovie.copy(2))
        val vm = buildViewModelWith(
            localData = localData,
            remoteData = remoteData
        )

        vm.state.test {
            Assert.assertEquals(UiState(), awaitItem())
            Assert.assertEquals(UiState(movies = localData), awaitItem())
            cancel()
        }
    }

    private fun buildViewModelWith(
        localData: List<Movie> = emptyList(),
        remoteData: List<Movie> = emptyList()
    ): MainViewModel {
        val moviesRepository = buildRepositoryWith(localData, remoteData)
        val getPopularMoviesUseCase = GetPopularMoviesUseCase(moviesRepository)
        val requestPopularMoviesUseCase = RequestPopularMoviesUseCase(moviesRepository)
        return MainViewModel(getPopularMoviesUseCase, requestPopularMoviesUseCase)
    }
}