package com.devexperto.architectcoders.composeui.screens.home

import androidx.compose.foundation.background
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.devexperto.architectcoders.R
import com.devexperto.architectcoders.composeui.screens.Screen
import com.devexperto.architectcoders.domain.Movie
import com.devexperto.architectcoders.ui.main.MainViewModel

@Composable
fun Home(vm: MainViewModel = viewModel()) {
    val state by vm.state.collectAsState()

    Screen {
        Scaffold(
            topBar = { HomeTopAppBar() },
        ) { padding ->
            state.movies?.let { MoviesGrid(it, Modifier.padding(padding)) }
        }
    }
}


@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun HomeTopAppBar() {
    TopAppBar(title = { Text(stringResource(id = R.string.app_name)) })
}

@Composable
private fun MoviesGrid(
    movies: List<Movie>,
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
            MovieItem(it)
        }
    }
}

@Composable
private fun MovieItem(movie: Movie) {
    Column(
        modifier = Modifier.fillMaxHeight()
    ) {
        AsyncImage(
            model = movie.posterPath,
            contentDescription = movie.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(2 / 3f)
        )
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