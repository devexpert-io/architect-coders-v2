package com.devexperto.architectcoders.ui.detail

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.devexperto.architectcoders.databinding.ActivityDetailBinding
import com.devexperto.architectcoders.model.Movie
import com.devexperto.architectcoders.ui.common.getParcelableExtraCompat
import com.devexperto.architectcoders.ui.common.loadUrl

class DetailActivity : AppCompatActivity() {
    companion object {
        const val MOVIE = "DetailActivity:movie"
    }

    private val viewModel: DetailViewModel by viewModels {
        DetailViewModelFactory(requireNotNull(intent.getParcelableExtraCompat(MOVIE)))
    }

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.state.observe(this) { updateUI(it.movie) }
    }

    private fun updateUI(movie: Movie) = with(binding) {
        movieDetailToolbar.title = movie.title
        movieDetailImage.loadUrl("https://image.tmdb.org/t/p/w780${movie.backdropPath}")
        movieDetailSummary.text = movie.overview
        movieDetailInfo.setMovie(movie)
    }
}