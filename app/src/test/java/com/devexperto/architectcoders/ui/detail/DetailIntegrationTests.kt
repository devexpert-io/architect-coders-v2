package com.devexperto.architectcoders.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.devexperto.architectcoders.data.server.RemoteMovie
import com.devexperto.architectcoders.ui.TestSchedulerProvider
import com.devexperto.architectcoders.ui.buildDatabaseMovies
import com.devexperto.architectcoders.ui.buildRepositoryWith
import com.devexperto.architectcoders.ui.detail.DetailViewModel.UiState
import com.devexperto.architectcoders.usecases.FindMovieUseCase
import com.devexperto.architectcoders.usecases.SwitchMovieFavoriteUseCase
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.argThat
import org.mockito.kotlin.verify
import com.devexperto.architectcoders.data.database.Movie as DatabaseMovie

@RunWith(MockitoJUnitRunner::class)
class DetailIntegrationTests {

    @get:Rule
    val instantTaskRule = InstantTaskExecutorRule()

    @Mock
    lateinit var observer: Observer<UiState>

    @Test
    fun `UI is updated with the movie on start`() {
        val vm = buildViewModelWith(
            id = 2,
            localData = buildDatabaseMovies(1, 2, 3)
        )

        vm.state.observeForever(observer)

        verify(observer).onChanged(argThat { movie!!.id == 2 })
    }

    @Test
    fun `Favorite is updated in local data source`() {
        val vm = buildViewModelWith(
            id = 2,
            localData = buildDatabaseMovies(1, 2, 3)
        )
        vm.state.observeForever(observer)

        vm.onFavoriteClicked()

        verify(observer).onChanged(argThat { movie!!.favorite })
    }

    private fun buildViewModelWith(
        id: Int,
        localData: List<DatabaseMovie> = emptyList(),
        remoteData: List<RemoteMovie> = emptyList()
    ): DetailViewModel {
        val moviesRepository = buildRepositoryWith(localData, remoteData)
        val findMovieUseCase = FindMovieUseCase(moviesRepository)
        val switchMovieFavoriteUseCase = SwitchMovieFavoriteUseCase(moviesRepository)
        return DetailViewModel(
            id,
            findMovieUseCase,
            switchMovieFavoriteUseCase,
            TestSchedulerProvider()
        )
    }
}