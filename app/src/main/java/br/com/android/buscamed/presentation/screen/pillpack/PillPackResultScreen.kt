package br.com.android.buscamed.presentation.screen.pillpack

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
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
import br.com.android.buscamed.domain.model.pillpack.PillPack
import br.com.android.buscamed.domain.model.pillpack.PillPackComponent
import br.com.android.buscamed.presentation.core.components.dialog.BaseMessageDialog
import br.com.android.buscamed.presentation.core.components.label.LabeledText
import br.com.android.buscamed.presentation.core.components.topbar.SimpleTopAppBar
import br.com.android.buscamed.presentation.screen.pillpack.PillPackResultUIState
import br.com.android.buscamed.presentation.viewmodel.PillPackResultViewModel

@Composable
fun PillPackResultScreen(
    viewModel: PillPackResultViewModel,
    onBackClick: () -> Unit
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    PillPackResultScreen(
        state = state,
        onBackClick = onBackClick
    )
}

@Composable
fun PillPackResultScreen(
    state: PillPackResultUIState,
    onBackClick: () -> Unit
) {
    Scaffold(
        topBar = {
            SimpleTopAppBar(
                title = stringResource(id = R.string.pillpack_result_screen_title),
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

            state.pillPack?.let { pillPack ->
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    item {
                        PillPackHeader(pillPack)
                    }

                    if (pillPack.components.isNotEmpty()) {
                        item {
                            SectionTitle(stringResource(R.string.label_active_ingredients))
                        }
                        items(pillPack.components) { component ->
                            PillPackComponentItem(component)
                        }
                    }

                    pillPack.usage?.let { usage ->
                        item {
                            SectionTitle(stringResource(R.string.label_usage_info))
                            PillPackUsageSection(
                                administrationRoutes = usage.administrationRoutes,
                                ageRestrictions = usage.ageRestrictions
                            )
                        }
                    }

                    if (pillPack.indications.isNotEmpty()) {
                        item {
                            SectionTitle(stringResource(R.string.label_indications))
                            Text(
                                text = pillPack.indications.joinToString(", "),
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.padding(bottom = 16.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun PillPackHeader(pillPack: PillPack) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = pillPack.medicationName ?: stringResource(id = R.string.label_unknown_medication),
                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
            
            Row(modifier = Modifier.fillMaxWidth().padding(top = 8.dp)) {
                LabeledText(
                    label = stringResource(R.string.label_expiration_date),
                    value = pillPack.expirationDate ?: "-",
                    modifier = Modifier.weight(1f)
                )
                LabeledText(
                    label = stringResource(R.string.label_batch),
                    value = pillPack.batch ?: "-",
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
fun SectionTitle(title: String) {
    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.primary
        )
        HorizontalDivider(modifier = Modifier.padding(top = 4.dp))
    }
}

@Composable
fun PillPackComponentItem(component: PillPackComponent) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Text(
            text = component.activeIngredient,
            modifier = Modifier.weight(1f),
            style = MaterialTheme.typography.bodyLarge
        )
        Text(
            text = "${component.dosageValue?.toPlainString() ?: ""} ${component.dosageUnit ?: ""}".trim(),
            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Medium)
        )
    }
}

@Composable
fun PillPackUsageSection(administrationRoutes: List<String>?, ageRestrictions: List<String>?) {
    Column(modifier = Modifier.padding(bottom = 16.dp)) {
        if (!administrationRoutes.isNullOrEmpty()) {
            LabeledText(
                label = stringResource(R.string.label_administration_routes),
                value = administrationRoutes.joinToString(", ")
            )
        }
        if (!ageRestrictions.isNullOrEmpty()) {
            LabeledText(
                label = stringResource(R.string.label_age_restrictions),
                value = ageRestrictions.joinToString(", "),
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}

private fun Double.toPlainString(): String {
    val stringValue = this.toString()
    return if (stringValue.endsWith(".0")) {
        stringValue.substring(0, stringValue.length - 2)
    } else {
        stringValue
    }
}