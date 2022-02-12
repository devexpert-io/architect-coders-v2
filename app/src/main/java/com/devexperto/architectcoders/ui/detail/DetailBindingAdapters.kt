package com.devexperto.architectcoders.ui.detail

import androidx.databinding.BindingAdapter
import com.devexperto.architectcoders.model.Movie

@BindingAdapter("movie")
fun MovieDetailInfoView.updateMovieDetails(movie: Movie?) {
    if (movie != null) {
        setMovie(movie)
    }
}