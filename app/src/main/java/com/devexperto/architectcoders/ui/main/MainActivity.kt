package com.devexperto.architectcoders.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.devexperto.architectcoders.databinding.ActivityMainBinding
import com.devexperto.architectcoders.model.Movie
import com.devexperto.architectcoders.model.MoviesRepository
import com.devexperto.architectcoders.ui.detail.DetailActivity

class MainActivity : AppCompatActivity(), MainPresenter.View {

    private val presenter by lazy {
        MainPresenter(MoviesRepository(this), lifecycleScope)
    }

    private val adapter = MoviesAdapter { presenter.onMovieClicked(it) }
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        presenter.onCreate(this@MainActivity)
        binding.recycler.adapter = adapter
    }

    override fun onDestroy() {
        presenter.onDestroy()
        super.onDestroy()
    }

    override fun showProgress() {
        binding.progress.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        binding.progress.visibility = View.GONE
    }

    override fun updateData(movies: List<Movie>) {
        adapter.submitList(movies)
    }

    override fun navigateTo(movie: Movie) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(DetailActivity.MOVIE, movie)
        startActivity(intent)
    }
}