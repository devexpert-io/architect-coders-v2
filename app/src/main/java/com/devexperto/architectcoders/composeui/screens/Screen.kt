package com.devexperto.architectcoders.composeui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.devexperto.architectcoders.composeui.ui.theme.ArchitectCodersTheme

@Composable
fun Screen(content: @Composable () -> Unit) {
    ArchitectCodersTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background,
            content = content
        )
    }
}