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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.com.android.buscamed.R
import br.com.android.buscamed.domain.model.medication.AnvisaMedication
import br.com.android.buscamed.presentation.core.components.dialog.BaseMessageDialog
import br.com.android.buscamed.presentation.core.components.loading.BaseLinearProgressIndicator
import br.com.android.buscamed.presentation.core.components.topbar.SimpleTopAppBar
import br.com.android.buscamed.presentation.screen.medication.state.MedicationCatalogUIState
import br.com.android.buscamed.presentation.viewmodel.MedicationCatalogViewModel

@Composable
fun MedicationCatalogScreen(
    viewModel: MedicationCatalogViewModel,
    onBackClick: () -> Unit,
    onMedicationClick: (MedicationLeafletScreenArgs) -> Unit
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    MedicationCatalogScreen(
        state = state,
        onBackClick = onBackClick,
        onMedicationClick = onMedicationClick,
        onLoadMore = viewModel::loadMedications
    )
}

@Composable
fun MedicationCatalogScreen(
    state: MedicationCatalogUIState,
    onBackClick: () -> Unit,
    onMedicationClick: (MedicationLeafletScreenArgs) -> Unit,
    onLoadMore: () -> Unit
) {
    Scaffold(
        topBar = {
            SimpleTopAppBar(
                title = stringResource(R.string.medication_catalog_screen_title),
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

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(state.medications) { medication ->
                    MedicationCatalogItem(
                        medication = medication,
                        onClick = { onMedicationClick(MedicationLeafletScreenArgs(medicationId = medication.id)) }
                    )
                }

                item {
                    if (state.hasMoreItems && state.medications.isNotEmpty() && !state.showLoading) {
                        LaunchedEffect(Unit) {
                            onLoadMore()
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun MedicationCatalogItem(
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
                text = medication.presentation,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}