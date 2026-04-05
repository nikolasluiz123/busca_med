package br.com.android.buscamed.presentation.screen.result

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.com.android.buscamed.domain.model.medication.AnvisaMedication
import br.com.android.buscamed.presentation.core.components.dialog.BaseMessageDialog
import br.com.android.buscamed.presentation.core.components.topbar.SimpleTopAppBar
import br.com.android.buscamed.presentation.screen.result.state.MedicationDetailsUIState
import br.com.android.buscamed.presentation.viewmodel.MedicationDetailsViewModel

/**
 * Componente Stateful para a tela de detalhes de um medicamento.
 *
 * @param viewModel ViewModel associado a esta tela.
 * @param medication Dados do medicamento a ser detalhado passado via navegação.
 * @param onBackClick Callback acionado para retornar.
 */
@Composable
fun MedicationDetailsScreen(
    viewModel: MedicationDetailsViewModel,
    onBackClick: () -> Unit
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    MedicationDetailsScreen(
        state = state,
        onBackClick = onBackClick
    )
}

/**
 * Componente Stateless para a tela de detalhes de um medicamento.
 *
 * @param state Estado atual da tela.
 * @param onBackClick Callback acionado para retornar.
 */
@Composable
fun MedicationDetailsScreen(
    state: MedicationDetailsUIState,
    onBackClick: () -> Unit
) {
    Scaffold(
        topBar = {
            SimpleTopAppBar(
                title = "Detalhes do Medicamento",
                onBackClick = onBackClick
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            state.medication?.let { med ->
                DetailSection(title = "Produto", content = med.productName)
                DetailSection(
                    title = "Princípio(s) Ativo(s)",
                    content = med.activeIngredients.joinToString(", ").ifEmpty { null }
                )
                DetailSection(title = "Apresentação", content = med.presentation)
                DetailSection(title = "Laboratório", content = med.laboratory)
                DetailSection(title = "CNPJ", content = med.cnpj)
                DetailSection(title = "Classe Terapêutica", content = med.therapeuticClass)
                DetailSection(title = "Tipo de Produto", content = med.productType)
                DetailSection(title = "Tarja", content = med.stripe)
                DetailSection(
                    title = "Restrição Hospitalar",
                    content = if (med.isHospitalRestriction) "Sim" else "Não"
                )

                med.ean1?.let { DetailSection(title = "EAN 1", content = it) }
                med.ean2?.let { DetailSection(title = "EAN 2", content = it) }
                med.ean3?.let { DetailSection(title = "EAN 3", content = it) }
            }
        }

        BaseMessageDialog(state = state.messageDialogState)
    }
}

/**
 * Componente visual para exibir uma sessão de detalhe com título e conteúdo.
 *
 * @param title Título da informação.
 * @param content Conteúdo textual a ser exibido.
 */
@Composable
private fun DetailSection(title: String, content: String?) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = content ?: "Não informado",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}