package br.com.android.buscamed.presentation.core.permissions

import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat

fun Context.verifyPermissionGranted(permission: String): Boolean {
    return ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED
}

fun ManagedActivityResultLauncher<PickVisualMediaRequest, Uri?>.launchVideosOnly() {
    this.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.VideoOnly))
}