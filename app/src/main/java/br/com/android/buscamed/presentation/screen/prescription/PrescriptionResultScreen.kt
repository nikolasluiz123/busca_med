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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.com.android.buscamed.R
import br.com.android.buscamed.domain.model.prescription.Duration
import br.com.android.buscamed.domain.model.prescription.Frequency
import br.com.android.buscamed.domain.model.prescription.PrescriptionMedication
import br.com.android.buscamed.domain.model.prescription.ValueUnit
import br.com.android.buscamed.presentation.core.components.dialog.BaseMessageDialog
import br.com.android.buscamed.presentation.core.components.label.LabeledText
import br.com.android.buscamed.presentation.core.components.topbar.SimpleTopAppBar
import br.com.android.buscamed.presentation.state.PrescriptionResultUIState
import br.com.android.buscamed.presentation.viewmodel.PrescriptionResultViewModel

/**
 * Tela responsável por exibir o resultado do processamento de uma prescrição médica.
 *
 * @param viewModel O ViewModel que gerencia o estado da tela.
 * @param onBackClick Ação executada ao solicitar o retorno para a tela anterior.
 */
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

/**
 * Componente visual da tela de resultados da prescrição.
 *
 * @param state O estado atual da interface gráfica.
 * @param onBackClick Ação executada ao solicitar o retorno para a tela anterior.
 */
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
 * encontrado na prescrição médica, utilizando [ConstraintLayout] para otimização de espaço.
 *
 * @param medication Dados estruturados do medicamento.
 */
@Composable
fun PrescriptionMedicationItem(medication: PrescriptionMedication) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerLowest
        )
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            val (
                nameRef,
                presentationRef,
                doseRef,
                frequencyRef,
                durationRef,
                totalRef
            ) = createRefs()

            Text(
                text = medication.name ?: stringResource(id = R.string.label_unknown_medication),
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.constrainAs(nameRef) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                }
            )

            createHorizontalChain(presentationRef, doseRef)

            LabeledText(
                label = stringResource(id = R.string.label_presentation),
                value = medication.presentationDosage.format(),
                modifier = Modifier
                    .constrainAs(presentationRef) {
                        top.linkTo(nameRef.bottom, margin = 16.dp)
                        width = Dimension.fillToConstraints
                    }
                    .padding(end = 8.dp)
            )

            LabeledText(
                label = stringResource(id = R.string.label_dose),
                value = medication.dose.format(),
                modifier = Modifier
                    .constrainAs(doseRef) {
                        top.linkTo(nameRef.bottom, margin = 16.dp)
                        width = Dimension.fillToConstraints
                    }
                    .padding(start = 8.dp)
            )

            val barrierRow1 = createBottomBarrier(presentationRef, doseRef)
            createHorizontalChain(frequencyRef, durationRef)

            LabeledText(
                label = stringResource(id = R.string.label_frequency),
                value = medication.frequency.format(),
                modifier = Modifier
                    .constrainAs(frequencyRef) {
                        top.linkTo(barrierRow1, margin = 16.dp)
                        width = Dimension.fillToConstraints
                    }
                    .padding(end = 8.dp)
            )

            LabeledText(
                label = stringResource(id = R.string.label_duration),
                value = medication.duration.format(),
                modifier = Modifier
                    .constrainAs(durationRef) {
                        top.linkTo(barrierRow1, margin = 16.dp)
                        width = Dimension.fillToConstraints
                    }
                    .padding(start = 8.dp)
            )

            val barrierRow2 = createBottomBarrier(frequencyRef, durationRef)

            LabeledText(
                label = stringResource(id = R.string.label_total_quantity),
                value = medication.totalPrescribedQuantity.format(),
                modifier = Modifier.constrainAs(totalRef) {
                    top.linkTo(barrierRow2, margin = 16.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                }
            )
        }
    }
}

private fun ValueUnit?.format(): String {
    if (this == null || this.value == null) return "-"
    val unitStr = this.unit ?: ""
    return "${this.value.toPlainString()} $unitStr".trim()
}

private fun Frequency?.format(): String {
    if (this == null || this.interval == null) return "-"
    val unitStr = this.unit ?: ""
    val baseText = "A cada ${this.interval.toPlainString()} $unitStr".trim()

    return if (!this.guidanceText.isNullOrBlank()) {
        "$baseText (${this.guidanceText})"
    } else {
        baseText
    }
}

private fun Duration?.format(): String {
    if (this == null) return "-"
    if (this.isContinuousUse) return "Uso contínuo"
    if (this.value == null) return "-"

    val unitStr = this.unit ?: ""
    return "${this.value.toPlainString()} $unitStr".trim()
}

private fun Double.toPlainString(): String {
    val stringValue = this.toString()
    return if (stringValue.endsWith(".0")) {
        stringValue.substring(0, stringValue.length - 2)
    } else {
        stringValue
    }
}