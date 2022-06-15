package com.devexperto.architectcoders.ui.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.devexperto.architectcoders.R
import com.devexperto.architectcoders.databinding.FragmentMainBinding
import com.devexperto.architectcoders.ui.common.app
import com.devexperto.architectcoders.ui.common.launchAndCollect
import javax.inject.Inject

class MainFragment : Fragment(R.layout.fragment_main) {

    @Inject
    lateinit var vmFactory: MainViewModelFactory

    private val viewModel: MainViewModel by viewModels { vmFactory }

    private lateinit var mainState: MainState

    private val adapter = MoviesAdapter { mainState.onMovieClicked(it) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app.component.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainState = buildMainState()

        val binding = FragmentMainBinding.bind(view).apply {
            recycler.adapter = adapter
        }

        viewLifecycleOwner.launchAndCollect(viewModel.state) {
            binding.loading = it.loading
            binding.movies = it.movies
            binding.error = it.error?.let(mainState::errorToString)
        }

        mainState.requestLocationPermission {
            viewModel.onUiReady()
        }
    }
}