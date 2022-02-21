package com.devexperto.architectcoders.ui.main

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.devexperto.architectcoders.R
import com.devexperto.architectcoders.databinding.ViewMovieBinding
import com.devexperto.architectcoders.model.database.Movie
import com.devexperto.architectcoders.ui.common.basicDiffUtil
import com.devexperto.architectcoders.ui.common.inflate

class MoviesAdapter(private val listener: (Movie) -> Unit) :
    ListAdapter<Movie, MoviesAdapter.ViewHolder>(basicDiffUtil { old, new -> old.id == new.id }) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = parent.inflate(R.layout.view_movie, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = getItem(position)
        holder.bind(movie)
        holder.itemView.setOnClickListener { listener(movie) }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ViewMovieBinding.bind(view)
        fun bind(movie: Movie) {
            binding.movie = movie
        }
    }
}