package br.com.android.buscamed.presentation.core.permissions

import android.Manifest
import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.READ_MEDIA_IMAGES
import android.Manifest.permission.READ_MEDIA_VISUAL_USER_SELECTED
import android.content.Context
import android.os.Build
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import br.com.android.buscamed.presentation.core.permissions.PermissionUtils.requestMultiplePermissionsLauncher

@Composable
fun RequestAllPermissions(context: Context) {
    val standardPermissionLauncher = requestMultiplePermissionsLauncher()

    LaunchedEffect(Unit) {
        val standardPermissions = mutableListOf<String>()
        addNotificationsPermission(context, standardPermissions)
        addCameraPermission(context, standardPermissions)
        addMediaPermissions(context, standardPermissions)

        if (standardPermissions.isNotEmpty()) {
            standardPermissionLauncher.launch(standardPermissions.toTypedArray())
        }
    }
}

private fun addMediaPermissions(
    context: Context,
    permissions: MutableList<String>
) {
    val requiredPermissions = when {
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE -> listOf(
            READ_MEDIA_IMAGES,
            READ_MEDIA_VISUAL_USER_SELECTED
        )

        Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU -> listOf(
            READ_MEDIA_IMAGES,
        )

        else -> listOf(READ_EXTERNAL_STORAGE)
    }

    requiredPermissions
        .filterNot { context.verifyPermissionGranted(it) }
        .forEach { permissions.add(it) }
}

private fun addCameraPermission(
    context: Context,
    permissions: MutableList<String>
) {
    if (!context.verifyPermissionGranted(Manifest.permission.CAMERA)) {
        permissions.add(Manifest.permission.CAMERA)
    }
}

private fun addNotificationsPermission(
    context: Context,
    permissions: MutableList<String>
) {
    if (!context.verifyPermissionGranted(Manifest.permission.POST_NOTIFICATIONS) &&
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU
    ) {
        permissions.add(Manifest.permission.POST_NOTIFICATIONS)
    }
}