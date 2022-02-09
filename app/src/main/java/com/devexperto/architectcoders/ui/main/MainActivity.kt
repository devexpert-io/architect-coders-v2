package com.devexperto.architectcoders.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.devexperto.architectcoders.databinding.ActivityMainBinding
import com.devexperto.architectcoders.model.Movie
import com.devexperto.architectcoders.model.MoviesRepository
import com.devexperto.architectcoders.ui.common.visible
import com.devexperto.architectcoders.ui.detail.DetailActivity
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels { MainViewModelFactory(MoviesRepository(this)) }

    private val adapter = MoviesAdapter { viewModel.onMovieClicked(it) }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.recycler.adapter = adapter

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.state.collect(::updateUI)
            }
        }
    }

    private fun updateUI(state: MainViewModel.UiState) {
        binding.progress.visible = state.loading
        state.movies?.let(adapter::submitList)
        state.navigateTo?.let(::navigateTo)
    }

    private fun navigateTo(movie: Movie) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(DetailActivity.MOVIE, movie)
        startActivity(intent)
    }
}