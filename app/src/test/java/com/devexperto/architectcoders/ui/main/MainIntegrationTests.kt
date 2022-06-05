package com.devexperto.architectcoders.ui.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.devexperto.architectcoders.TestSchedulerProvider
import com.devexperto.architectcoders.data.server.RemoteMovie
import com.devexperto.architectcoders.ui.buildDatabaseMovies
import com.devexperto.architectcoders.ui.buildRemoteMovies
import com.devexperto.architectcoders.ui.buildRepositoryWith
import com.devexperto.architectcoders.ui.main.MainViewModel.UiState
import com.devexperto.architectcoders.usecases.GetPopularMoviesUseCase
import com.devexperto.architectcoders.usecases.RequestPopularMoviesUseCase
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.argThat
import org.mockito.kotlin.verify
import com.devexperto.architectcoders.data.database.Movie as DatabaseMovie

@RunWith(MockitoJUnitRunner::class)
class MainIntegrationTests {

    @get:Rule
    val instantTaskRule = InstantTaskExecutorRule()

    @Mock
    lateinit var observer: Observer<UiState>

    @Test
    fun `data is loaded from server when local source is empty`() {
        val remoteData = buildRemoteMovies(4, 5, 6)
        val vm = buildViewModelWith(
            localData = emptyList(),
            remoteData = remoteData
        )

        vm.onUiReady()

        vm.state.observeForever(observer)

        verify(observer).onChanged(argThat {
            movies!!.map { it.title } == remoteData.map { it.title }
        })
    }

    @Test
    fun `data is loaded from local source when available`() {
        val localData = buildDatabaseMovies(1, 2, 3)
        val remoteData = buildRemoteMovies(4, 5, 6)
        val vm = buildViewModelWith(
            localData = localData,
            remoteData = remoteData
        )

        vm.state.observeForever(observer)

        verify(observer).onChanged(argThat {
            movies!!.map { it.title } == localData.map { it.title }
        })
    }

    private fun buildViewModelWith(
        localData: List<DatabaseMovie> = emptyList(),
        remoteData: List<RemoteMovie> = emptyList()
    ): MainViewModel {
        val moviesRepository = buildRepositoryWith(localData, remoteData)
        val getPopularMoviesUseCase = GetPopularMoviesUseCase(moviesRepository)
        val requestPopularMoviesUseCase = RequestPopularMoviesUseCase(moviesRepository)
        return MainViewModel(
            getPopularMoviesUseCase,
            requestPopularMoviesUseCase,
            TestSchedulerProvider()
        )
    }
}