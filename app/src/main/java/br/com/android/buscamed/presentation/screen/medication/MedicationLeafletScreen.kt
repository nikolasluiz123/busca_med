package br.com.android.buscamed.presentation.screen.medication

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.com.android.buscamed.R
import br.com.android.buscamed.presentation.core.components.dialog.BaseMessageDialog
import br.com.android.buscamed.presentation.core.components.loading.BaseLinearProgressIndicator
import br.com.android.buscamed.presentation.core.components.topbar.SimpleTopAppBar
import br.com.android.buscamed.presentation.screen.medication.state.MedicationLeafletUIState
import br.com.android.buscamed.presentation.viewmodel.MedicationLeafletViewModel

@Composable
fun MedicationLeafletScreen(
    viewModel: MedicationLeafletViewModel,
    onBackClick: () -> Unit
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    MedicationLeafletScreen(
        state = state,
        onBackClick = onBackClick
    )
}

@Composable
fun MedicationLeafletScreen(
    state: MedicationLeafletUIState,
    onBackClick: () -> Unit
) {
    Scaffold(
        topBar = {
            SimpleTopAppBar(
                title = stringResource(R.string.medication_leaflet_screen_title),
                onBackClick = onBackClick
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            BaseLinearProgressIndicator(state.showLoading)
            BaseMessageDialog(state = state.messageDialogState)

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                state.patientLeaflet?.let { leaflet ->
                    Text(
                        text = stringResource(R.string.label_patient_leaflet),
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )

                    LeafletTextSection("Indicações", leaflet.indications.joinToString("\n"))
                    LeafletTextSection("Como usar", leaflet.howToUse)
                    LeafletTextSection("Mecanismo de ação", leaflet.mechanismOfAction)
                    LeafletTextSection("Dose esquecida", leaflet.missedDose)
                    LeafletListSection("Efeitos colaterais comuns", leaflet.commonSideEffects)
                    LeafletListSection("Contraindicações", leaflet.contraindications)
                    LeafletListSection("Interações a evitar", leaflet.interactionsToAvoid)
                    LeafletListSection("Precauções e avisos", leaflet.precautionsAndWarnings)

                    Divider(modifier = Modifier.padding(vertical = 8.dp))
                }

                state.professionalLeaflet?.let { leaflet ->
                    Text(
                        text = stringResource(R.string.label_professional_leaflet),
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )

                    LeafletTextSection("Propriedades Farmacológicas", leaflet.pharmacologicalProperties)
                    LeafletListSection("Indicações e Espectro", leaflet.indicationsAndSpectrum)
                    LeafletListSection("Ajustes de dosagem", leaflet.dosageAdjustments)
                    LeafletListSection("Avisos clínicos", leaflet.clinicalWarnings)
                    LeafletListSection("Reações adversas", leaflet.adverseReactions)
                    LeafletListSection("Interações medicamentosas", leaflet.drugInteractions)
                    LeafletListSection("Interferências em exames laboratoriais", leaflet.labTestInterferences)
                }
            }
        }
    }
}

@Composable
private fun LeafletTextSection(title: String, content: String?) {
    if (!content.isNullOrBlank()) {
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = content,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
private fun LeafletListSection(title: String, items: List<String>?) {
    if (!items.isNullOrEmpty()) {
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
            items.forEach { item ->
                Text(
                    text = "• $item",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}