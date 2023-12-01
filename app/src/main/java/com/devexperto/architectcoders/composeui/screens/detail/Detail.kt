package com.devexperto.architectcoders.composeui.screens.detail

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.devexperto.architectcoders.ui.detail.DetailViewModel

@Composable
fun Detail(vm: DetailViewModel = hiltViewModel()) {

    val state = vm.state.collectAsState()

    Text("Detail Screen ${state.value.movie?.title}")
}