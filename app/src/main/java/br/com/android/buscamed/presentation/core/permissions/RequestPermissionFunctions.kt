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

/**
 * Solicita todas as permissões essenciais para o funcionamento do aplicativo.
 *
 * Esta função composable avalia as necessidades de permissão (notificação, câmera e mídia)
 * com base na versão do sistema operacional e no estado atual das concessões,
 * disparando as solicitações necessárias de forma centralizada.
 *
 * @param context O contexto da aplicação utilizado para verificação de permissões existentes.
 */
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

/**
 * Adiciona as permissões de acesso a mídia à lista de solicitações pendentes.
 *
 * A função adapta a solicitação conforme o nível da API do dispositivo, lidando
 * com as mudanças introduzidas no Android 13 (Tiramisu) e Android 14 (Upside Down Cake).
 */
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

/**
 * Adiciona a permissão de uso da câmera à lista de solicitações pendentes.
 */
private fun addCameraPermission(
    context: Context,
    permissions: MutableList<String>
) {
    if (!context.verifyPermissionGranted(Manifest.permission.CAMERA)) {
        permissions.add(Manifest.permission.CAMERA)
    }
}

/**
 * Adiciona a permissão de postagem de notificações à lista de solicitações pendentes.
 *
 * Esta função verifica se a permissão é necessária com base na versão do sistema (API 33+).
 */
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
