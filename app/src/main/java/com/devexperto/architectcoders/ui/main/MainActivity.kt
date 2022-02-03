package com.devexperto.architectcoders.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.devexperto.architectcoders.databinding.ActivityMainBinding
import com.devexperto.architectcoders.model.MoviesRepository
import com.devexperto.architectcoders.ui.detail.DetailActivity
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val moviesRepository by lazy { MoviesRepository(this) }

    private val adapter = MoviesAdapter {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(DetailActivity.MOVIE, it)
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.recycler.adapter = adapter

        lifecycleScope.launch {
            adapter.submitList(moviesRepository.findPopularMovies().results)
        }
    }
}