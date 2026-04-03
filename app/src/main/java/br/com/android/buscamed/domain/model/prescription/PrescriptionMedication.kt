package br.com.android.buscamed.domain.model.prescription

/**
 * Representa os dados extraídos de um medicamento.
 *
 * @property name Nome do medicamento.
 * @property presentationDosage Apresentação e dosagem.
 * @property dose Quantidade a ser ingerida por vez.
 * @property frequency Frequência de uso.
 * @property duration Duração do tratamento.
 * @property totalPrescribedQuantity Quantidade total prescrita.
 */
data class PrescriptionMedication(
    val name: String?,
    val presentationDosage: ValueUnit?,
    val dose: ValueUnit?,
    val frequency: Frequency?,
    val duration: Duration?,
    val totalPrescribedQuantity: ValueUnit?
)