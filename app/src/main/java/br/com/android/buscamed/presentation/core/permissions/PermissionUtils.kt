package br.com.android.buscamed.presentation.core.permissions

import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable

/**
 * Utilitário para gerenciamento de permissões do sistema Android.
 *
 * Provê funções facilitadoras para lidar com o fluxo de solicitação de permissões
 * dentro do ecossistema Jetpack Compose.
 */
object PermissionUtils {

    /**
     * Cria e retorna um launcher para solicitação de múltiplas permissões.
     *
     * Esta função utiliza o contrato [ActivityResultContracts.RequestMultiplePermissions]
     * para gerenciar as requisições de forma reativa no Compose.
     *
     * @param onResult Função callback executada após a resposta do usuário, fornecendo um mapa com o status de cada permissão.
     * @return Um [ManagedActivityResultLauncher] pronto para disparar a solicitação de permissões.
     */
    @Composable
    fun requestMultiplePermissionsLauncher(onResult: (Map<String, Boolean>) -> Unit = { }): ManagedActivityResultLauncher<Array<String>, Map<String, Boolean>> {
        return rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestMultiplePermissions(),
            onResult = onResult
        )
    }
}
