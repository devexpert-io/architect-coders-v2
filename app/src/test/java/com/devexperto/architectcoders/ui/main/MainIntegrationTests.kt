package com.devexperto.architectcoders.ui.main

import app.cash.turbine.test
import com.devexperto.architectcoders.data.server.RemoteMovie
import com.devexperto.architectcoders.testrules.CoroutinesTestRule
import com.devexperto.architectcoders.appTestShared.buildDatabaseMovies
import com.devexperto.architectcoders.appTestShared.buildRemoteMovies
import com.devexperto.architectcoders.appTestShared.buildRepositoryWith
import com.devexperto.architectcoders.ui.main.MainViewModel.UiState
import com.devexperto.architectcoders.usecases.GetPopularMoviesUseCase
import com.devexperto.architectcoders.usecases.RequestPopularMoviesUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import com.devexperto.architectcoders.data.database.Movie as DatabaseMovie

@ExperimentalCoroutinesApi
class MainIntegrationTests {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @Test
    fun `data is loaded from server when local source is empty`() = runTest {
        val remoteData = buildRemoteMovies(4, 5, 6)
        val vm = buildViewModelWith(
            localData = emptyList(),
            remoteData = remoteData
        )

        vm.onUiReady()

        vm.state.test {
            assertEquals(UiState(), awaitItem())
            assertEquals(UiState(movies = emptyList()), awaitItem())
            assertEquals(UiState(movies = emptyList(), loading = true), awaitItem())
            assertEquals(UiState(movies = emptyList(), loading = false), awaitItem())

            val movies = awaitItem().movies!!
            assertEquals("Title 4", movies[0].title)
            assertEquals("Title 5", movies[1].title)
            assertEquals("Title 6", movies[2].title)

            cancel()
        }
    }

    @Test
    fun `data is loaded from local source when available`() = runTest {
        val localData = buildDatabaseMovies(1, 2, 3)
        val remoteData = buildRemoteMovies(4, 5, 6)
        val vm = buildViewModelWith(
            localData = localData,
            remoteData = remoteData
        )

        vm.state.test {
            assertEquals(UiState(), awaitItem())

            val movies = awaitItem().movies!!
            assertEquals("Title 1", movies[0].title)
            assertEquals("Title 2", movies[1].title)
            assertEquals("Title 3", movies[2].title)

            cancel()
        }
    }

    private fun buildViewModelWith(
        localData: List<DatabaseMovie> = emptyList(),
        remoteData: List<RemoteMovie> = emptyList()
    ): MainViewModel {
        val moviesRepository = buildRepositoryWith(localData, remoteData)
        val getPopularMoviesUseCase = GetPopularMoviesUseCase(moviesRepository)
        val requestPopularMoviesUseCase = RequestPopularMoviesUseCase(moviesRepository)
        return MainViewModel(getPopularMoviesUseCase, requestPopularMoviesUseCase)
    }
}