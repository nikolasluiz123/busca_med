package br.com.android.buscamed.data.datasource.firestore

import br.com.android.buscamed.data.document.AnvisaMedicationDocument

/**
 * Contrato para a fonte de dados de medicamentos.
 */
interface MedicationDataSource {
    
    /**
     * Busca uma lista de documentos de medicamentos filtrando pelo código EAN.
     *
     * @param ean O código de barras a ser pesquisado.
     * @return Uma lista de [AnvisaMedicationDocument] correspondentes.
     */
    suspend fun getMedicationsByEan(ean: String): List<AnvisaMedicationDocument>
}