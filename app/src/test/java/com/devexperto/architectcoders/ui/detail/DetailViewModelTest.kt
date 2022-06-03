package com.devexperto.architectcoders.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.devexperto.architectcoders.testshared.sampleMovie
import com.devexperto.architectcoders.ui.TestSchedulerProvider
import com.devexperto.architectcoders.ui.detail.DetailViewModel.UiState
import com.devexperto.architectcoders.usecases.FindMovieUseCase
import com.devexperto.architectcoders.usecases.SwitchMovieFavoriteUseCase
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever


@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class DetailViewModelTest {

    @get:Rule
    val instantTaskRule = InstantTaskExecutorRule()

    @Mock
    lateinit var findMovieUseCase: FindMovieUseCase

    @Mock
    lateinit var switchMovieFavoriteUseCase: SwitchMovieFavoriteUseCase

    private lateinit var vm: DetailViewModel

    private val movie = sampleMovie.copy(id = 2)

    @Mock
    lateinit var observer: Observer<UiState>

    @Before
    fun setup() {
        whenever(findMovieUseCase(2)).thenReturn(Flowable.just(movie))
        whenever(switchMovieFavoriteUseCase(movie)).thenReturn(Completable.complete())
        vm = DetailViewModel(
            2,
            findMovieUseCase,
            switchMovieFavoriteUseCase,
            TestSchedulerProvider()
        )
    }

    @Test
    fun `UI is updated with the movie on start`() {
        vm.state.observeForever(observer)

        verify(observer).onChanged(UiState(movie = movie))
    }

    @Test
    fun `Favorite action calls the corresponding use case`() {
        vm.onFavoriteClicked()

        verify(switchMovieFavoriteUseCase).invoke(movie)
    }
}