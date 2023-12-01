package com.devexperto.architectcoders.ui.common

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect

@Composable
fun PermissionRequestEffect(permission: String, onResult: (Boolean) -> Unit) {
    val permissionLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) {
            onResult(it)
        }

    LaunchedEffect(Unit) {
        permissionLauncher.launch(permission)
    }
}