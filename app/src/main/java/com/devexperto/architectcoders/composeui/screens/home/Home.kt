package com.devexperto.architectcoders.composeui.screens.home

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.devexperto.architectcoders.R
import com.devexperto.architectcoders.composeui.screens.Screen
import com.devexperto.architectcoders.composeui.screens.common.ErrorText
import com.devexperto.architectcoders.composeui.screens.common.Loading
import com.devexperto.architectcoders.domain.Movie
import com.devexperto.architectcoders.ui.main.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(vm: MainViewModel = hiltViewModel(), onMovieClick: (Movie) -> Unit) {
    val state by vm.state.collectAsState()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    Screen {
        Scaffold(
            topBar = { HomeTopAppBar(scrollBehavior = scrollBehavior) },
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        ) { padding ->

            if (state.loading) {
                Loading(modifier = Modifier.padding(padding))
            }

            state.movies?.let {
                MoviesGrid(
                    movies = it,
                    onMovieClick = onMovieClick,
                    modifier = Modifier.padding(padding)
                )
            }

            state.error?.let { error ->
                ErrorText(
                    error = error,
                    modifier = Modifier.padding(padding)
                )
            }
        }
    }
}


@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun HomeTopAppBar(scrollBehavior: TopAppBarScrollBehavior) {
    TopAppBar(
        title = { Text(stringResource(id = R.string.app_name)) },
        scrollBehavior = scrollBehavior
    )
}