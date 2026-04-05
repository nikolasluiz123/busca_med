package br.com.android.buscamed.domain.model.prescription

import br.com.android.buscamed.domain.model.generic.ValueUnit

/**
 * Represents the extracted data of a medication.
 */
data class PrescriptionMedication(
    val name: String?,
    val presentationDosage: ValueUnit?,
    val dose: ValueUnit?,
    val frequency: Frequency?,
    val duration: Duration?,
    val totalPrescribedQuantity: ValueUnit?
)