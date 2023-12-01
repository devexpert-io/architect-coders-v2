package com.devexperto.architectcoders.composeui.screens.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.devexperto.architectcoders.R
import com.devexperto.architectcoders.domain.Error

@Composable
fun ErrorText(error: Error, modifier: Modifier) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        Text(
            text = error.toUiString(),
            textAlign = TextAlign.Center,
            modifier = Modifier.align(Alignment.Center),
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.error
        )
    }
}

@Composable
private fun Error.toUiString() = when (this) {
    Error.Connectivity -> stringResource(R.string.connectivity_error)
    is Error.Server -> stringResource(R.string.server_error) + code
    is Error.Unknown -> stringResource(R.string.unknown_error) + message
}