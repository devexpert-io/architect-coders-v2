package com.devexperto.architectcoders.ui.detail

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.text.bold
import androidx.core.text.buildSpannedString
import com.devexperto.architectcoders.model.database.Movie

class MovieDetailInfoView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {

    fun setMovie(movie: Movie) = with(movie) {
        text = buildSpannedString {

            bold { append("Original language: ") }
            appendLine(originalLanguage)

            bold { append("Original title: ") }
            appendLine(originalTitle)

            bold { append("Release date: ") }
            appendLine(releaseDate)

            bold { append("Popularity: ") }
            appendLine(popularity.toString())

            bold { append("Vote Average: ") }
            append(voteAverage.toString())
        }
    }
}