package com.devexperto.architectcoders.composeui.screens.home

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.devexperto.architectcoders.composeui.screens.Screen
import com.devexperto.architectcoders.ui.main.MainViewModel

@Composable
fun Home(vm: MainViewModel = viewModel()) {
    val state by vm.state.collectAsState()

    Screen {
        Scaffold { padding ->
            LazyColumn(modifier = Modifier.padding(padding)) {

                state.movies?.let { movies ->
                    items(movies) { movie ->
                        Text(text = movie.title)
                    }
                }
            }
        }
    }
}