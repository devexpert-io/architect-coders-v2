package com.devexperto.architectcoders.ui.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.devexperto.architectcoders.TestSchedulerProvider
import com.devexperto.architectcoders.testshared.sampleMovie
import com.devexperto.architectcoders.ui.main.MainViewModel.UiState
import com.devexperto.architectcoders.usecases.GetPopularMoviesUseCase
import com.devexperto.architectcoders.usecases.RequestPopularMoviesUseCase
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever


@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {

    @get:Rule
    val instantTaskRule = InstantTaskExecutorRule()

    @Mock
    lateinit var getPopularMoviesUseCase: GetPopularMoviesUseCase

    @Mock
    lateinit var requestPopularMoviesUseCase: RequestPopularMoviesUseCase

    private lateinit var vm: MainViewModel

    private val movies = listOf(sampleMovie.copy(id = 1))

    @Mock
    lateinit var observer: Observer<UiState>

    @Before
    fun setup() {
        whenever(getPopularMoviesUseCase()).thenReturn(Flowable.just(movies))
        whenever(requestPopularMoviesUseCase()).thenReturn(Completable.complete())
        vm = MainViewModel(
            getPopularMoviesUseCase,
            requestPopularMoviesUseCase,
            TestSchedulerProvider()
        )
    }

    @Test
    fun `State is updated with current cached content immediately`() {
        vm.state.observeForever(observer)

        verify(observer).onChanged(UiState(movies = movies))
    }


    @Test
    fun `Progress is shown when screen starts and hidden when it finishes requesting movies`() {
        vm.state.observeForever(observer)

        vm.onUiReady()

        verify(observer, times(2)).onChanged(UiState(movies = movies))
        verify(observer).onChanged(UiState(movies = movies, loading = true))
    }

    @Test
    fun `Popular movies are requested when UI screen starts`() {

        vm.onUiReady()

        verify(requestPopularMoviesUseCase).invoke()
    }
}