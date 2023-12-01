package com.devexperto.architectcoders.composeui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.devexperto.architectcoders.R
import com.devexperto.architectcoders.domain.Movie

@Composable
fun MoviesGrid(
    movies: List<Movie>,
    onMovieClick: (Movie) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(120.dp),
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        contentPadding = PaddingValues(4.dp)
    ) {
        items(movies) {
            MovieItem(
                movie = it,
                onMovieClick = { onMovieClick(it) }
            )
        }
    }
}

@Composable
private fun MovieItem(movie: Movie, onMovieClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .clickable { onMovieClick() }
    ) {
        Box {
            AsyncImage(
                model = movie.posterPath,
                contentDescription = movie.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(2 / 3f)
            )
            if (movie.favorite) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = stringResource(id = R.string.favorite),
                    tint = Color.White,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(4.dp)
                )
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primaryContainer)
                .padding(8.dp)
                .heightIn(48.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = movie.title,
                textAlign = TextAlign.Center,
                maxLines = 2
            )
        }
    }
}