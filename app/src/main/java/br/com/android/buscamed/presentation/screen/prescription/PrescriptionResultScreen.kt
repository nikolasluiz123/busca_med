package br.com.android.buscamed.presentation.screen.prescription

import androidx.compose.foundation.layout.Column
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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.com.android.buscamed.R
import br.com.android.buscamed.domain.model.prescription.PrescriptionMedication
import br.com.android.buscamed.presentation.core.components.dialog.BaseMessageDialog
import br.com.android.buscamed.presentation.core.components.topbar.SimpleTopAppBar
import br.com.android.buscamed.presentation.state.PrescriptionResultUIState
import br.com.android.buscamed.presentation.viewmodel.PrescriptionResultViewModel

@Composable
fun PrescriptionResultScreen(
    viewModel: PrescriptionResultViewModel,
    onBackClick: () -> Unit
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    PrescriptionResultScreen(
        state = state,
        onBackClick = onBackClick
    )
}

@Composable
fun PrescriptionResultScreen(
    state: PrescriptionResultUIState,
    onBackClick: () -> Unit
) {
    Scaffold(
        topBar = {
            SimpleTopAppBar(
                title = stringResource(id = R.string.prescription_result_screen_title),
                showNavigationIcon = true,
                onBackClick = onBackClick
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
        ) {
            BaseMessageDialog(state = state.messageDialogState)

            state.prescription?.let { prescription ->
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(prescription.medications) { medication ->
                        PrescriptionMedicationItem(medication = medication)
                    }
                }
            }
        }
    }
}

/**
 * Componente que renderiza as informações detalhadas de um único item medicamentoso
 * encontrado na prescrição médica.
 *
 * @param medication Dados estruturados do medicamento.
 */
@Composable
fun PrescriptionMedicationItem(medication: PrescriptionMedication) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = medication.toString(),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}