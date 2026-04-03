package br.com.android.buscamed.domain.model.prescription

/**
 * Representa uma prescrição médica estruturada após o processamento.
 *
 * @property medications Lista de medicamentos identificados na prescrição.
 */
data class Prescription(
    val medications: List<PrescriptionMedication>
)