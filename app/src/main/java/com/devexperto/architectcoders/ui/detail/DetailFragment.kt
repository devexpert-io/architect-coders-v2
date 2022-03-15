package com.devexperto.architectcoders.ui.detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.devexperto.architectcoders.R
import com.devexperto.architectcoders.databinding.FragmentDetailBinding
import com.devexperto.architectcoders.ui.common.launchAndCollect
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class DetailFragment : Fragment(R.layout.fragment_detail) {

    private val safeArgs: DetailFragmentArgs by navArgs()

    private val viewModel: DetailViewModel by viewModel {
        parametersOf(safeArgs.movieId)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentDetailBinding.bind(view)

        binding.movieDetailToolbar.setNavigationOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
        binding.movieDetailFavorite.setOnClickListener { viewModel.onFavoriteClicked() }

        viewLifecycleOwner.launchAndCollect(viewModel.state) { state ->
            if (state.movie != null) {
                binding.movie = state.movie
            }
        }
    }
}