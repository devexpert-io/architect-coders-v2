package com.devexperto.architectcoders.data

import android.Manifest
import android.app.Application
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import org.koin.core.annotation.Factory

@Factory
class AndroidPermissionChecker(private val application: Application) :
    PermissionChecker {

    override fun check(permission: PermissionChecker.Permission): Boolean =
        ContextCompat.checkSelfPermission(
            application,
            permission.toAndroidId()
        ) == PackageManager.PERMISSION_GRANTED
}

private fun PermissionChecker.Permission.toAndroidId() = when (this) {
    PermissionChecker.Permission.COARSE_LOCATION -> Manifest.permission.ACCESS_COARSE_LOCATION
}