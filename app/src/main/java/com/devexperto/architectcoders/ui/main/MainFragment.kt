package com.devexperto.architectcoders.ui.main

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.devexperto.architectcoders.R
import com.devexperto.architectcoders.databinding.FragmentMainBinding
import com.devexperto.architectcoders.model.Movie
import com.devexperto.architectcoders.model.MoviesRepository
import com.devexperto.architectcoders.ui.common.launchAndCollect
import com.devexperto.architectcoders.ui.common.visible

class MainFragment : Fragment(R.layout.fragment_main) {

    private val viewModel: MainViewModel by viewModels {
        MainViewModelFactory(MoviesRepository(requireActivity() as AppCompatActivity))
    }

    private val adapter = MoviesAdapter { viewModel.onMovieClicked(it) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentMainBinding.bind(view).apply {
            recycler.adapter = adapter
        }

        viewLifecycleOwner.launchAndCollect(viewModel.state) { binding.updateUI(it) }

        viewLifecycleOwner.launchAndCollect(viewModel.events) {
            when (it) {
                is MainViewModel.UiEvent.NavigateTo -> navigateTo(it.movie)
            }
        }
    }

    private fun FragmentMainBinding.updateUI(state: MainViewModel.UiState) {
        progress.visible = state.loading
        state.movies?.let(adapter::submitList)
    }

    private fun navigateTo(movie: Movie) {
        val action = MainFragmentDirections.actionMainToDetail(movie)
        findNavController().navigate(action)
    }
}