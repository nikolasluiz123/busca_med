package br.com.android.buscamed.presentation.screen.medication

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.com.android.buscamed.domain.model.medication.AnvisaMedication
import br.com.android.buscamed.presentation.core.components.dialog.BaseMessageDialog
import br.com.android.buscamed.presentation.core.components.topbar.SimpleTopAppBar
import br.com.android.buscamed.presentation.screen.result.MedicationDetailsArgs
import br.com.android.buscamed.presentation.screen.result.MedicationListArgs
import br.com.android.buscamed.presentation.screen.result.state.MedicationListUIState
import br.com.android.buscamed.presentation.viewmodel.MedicationListViewModel

/**
 * Componente Stateful para a tela de listagem de medicamentos.
 *
 * @param viewModel ViewModel associado a esta tela.
 * @param medications Lista de medicamentos passada via navegação.
 * @param onBackClick Callback acionado para retornar.
 * @param onMedicationClick Callback acionado ao selecionar um medicamento.
 */
@Composable
fun MedicationListScreen(
    viewModel: MedicationListViewModel,
    onBackClick: () -> Unit,
    onMedicationClick: (MedicationDetailsArgs) -> Unit
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    MedicationListScreen(
        state = state,
        onBackClick = onBackClick,
        onMedicationClick = onMedicationClick
    )
}

/**
 * Componente Stateless para a tela de listagem de medicamentos.
 *
 * @param state Estado atual da tela.
 * @param onBackClick Callback acionado para retornar.
 * @param onMedicationClick Callback acionado ao selecionar um medicamento.
 */
@Composable
fun MedicationListScreen(
    state: MedicationListUIState,
    onBackClick: () -> Unit,
    onMedicationClick: (MedicationDetailsArgs) -> Unit
) {
    Scaffold(
        topBar = {
            SimpleTopAppBar(
                title = "Medicamentos Encontrados",
                onBackClick = onBackClick
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(state.medications) { medication ->
                    MedicationListItem(
                        medication = medication,
                        onClick = { onMedicationClick(MedicationDetailsArgs(medication = medication)) }
                    )
                }
            }
        }

        BaseMessageDialog(state = state.messageDialogState)
    }
}

/**
 * Componente visual de um item da lista de medicamentos.
 *
 * @param medication Dados do medicamento a ser exibido.
 * @param onClick Callback acionado ao clicar no card.
 */
@Composable
private fun MedicationListItem(
    medication: AnvisaMedication,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = medication.productName,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = medication.activeIngredients.joinToString(", ").ifEmpty { "Princípio ativo indisponível" },
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = medication.presentation,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}