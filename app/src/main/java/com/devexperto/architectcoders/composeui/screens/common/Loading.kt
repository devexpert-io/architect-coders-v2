package com.devexperto.architectcoders.composeui.screens.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun Loading(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        CircularProgressIndicator(
            modifier = Modifier.align(Alignment.Center)
        )
    }
}