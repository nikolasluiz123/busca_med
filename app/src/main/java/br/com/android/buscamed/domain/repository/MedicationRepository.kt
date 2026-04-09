package br.com.android.buscamed.domain.repository

import br.com.android.buscamed.domain.core.pagination.PaginatedResult
import br.com.android.buscamed.domain.model.medication.AnvisaMedication
import br.com.android.buscamed.domain.model.medication.leaflet.PatientLeaflet
import br.com.android.buscamed.domain.model.medication.leaflet.ProfessionalLeaflet


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

    suspend fun getPaginatedMedications(limit: Int, cursor: Any?): PaginatedResult<AnvisaMedication>

    suspend fun getPatientLeaflet(medicationId: String): PatientLeaflet?

    suspend fun getProfessionalLeaflet(medicationId: String): ProfessionalLeaflet?
}