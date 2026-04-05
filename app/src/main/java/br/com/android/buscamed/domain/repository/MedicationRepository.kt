package br.com.android.buscamed.domain.repository

import br.com.android.buscamed.domain.model.medication.AnvisaMedication


/**
 * Contrato para acesso aos dados de medicamentos.
 */
interface MedicationRepository {
    /**
     * Pesquisa medicamentos utilizando o código de barras EAN.
     *
     * @param ean O código de barras a ser pesquisado.
     * @return Uma lista de medicamentos correspondentes ao EAN fornecido.
     */
    suspend fun getMedicationsByEan(ean: String): List<AnvisaMedication>
}