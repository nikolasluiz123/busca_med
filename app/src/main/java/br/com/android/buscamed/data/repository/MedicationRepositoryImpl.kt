package br.com.android.buscamed.data.repository

import br.com.android.buscamed.data.datasource.firestore.MedicationDataSource
import br.com.android.buscamed.data.datasource.remote.dto.leaflet.PatientLeafletDTO
import br.com.android.buscamed.data.datasource.remote.dto.leaflet.ProfessionalLeafletDTO
import br.com.android.buscamed.data.gson.defaultGSon
import br.com.android.buscamed.data.mapper.toDomain
import br.com.android.buscamed.domain.core.pagination.PaginatedResult
import br.com.android.buscamed.domain.model.medication.AnvisaMedication
import br.com.android.buscamed.domain.model.medication.leaflet.LeafletType
import br.com.android.buscamed.domain.model.medication.leaflet.PatientLeaflet
import br.com.android.buscamed.domain.model.medication.leaflet.ProfessionalLeaflet
import br.com.android.buscamed.domain.repository.MedicationRepository
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import javax.inject.Inject

/**
 * Implementação do repositório de medicamentos.
 *
 * @property dataSource Fonte de dados responsável por prover os documentos de medicamentos.
 */
class MedicationRepositoryImpl @Inject constructor(
    private val dataSource: MedicationDataSource
) : MedicationRepository {

    override suspend fun getMedicationsByEan(ean: String): List<AnvisaMedication> {
        val documents = dataSource.getMedicationsByEan(ean)
        return documents.map { it.toDomain() }
    }

    override suspend fun getPaginatedMedications(
        limit: Int,
        cursor: Any?
    ): PaginatedResult<AnvisaMedication> {
        val (documents, nextCursor) = dataSource.getPaginatedMedications(limit, cursor)
        return PaginatedResult(
            items = documents.map { it.toDomain() },
            cursor = nextCursor
        )
    }

    override suspend fun getPatientLeaflet(medicationId: String): PatientLeaflet? {
        val document = dataSource.getLeaflet(medicationId, LeafletType.PATIENT.documentId)
        val jsonString = document?.leafletResume ?: return null
        val dto = GsonBuilder().defaultGSon().fromJson(jsonString, PatientLeafletDTO::class.java)

        return dto.toDomain()
    }

    override suspend fun getProfessionalLeaflet(medicationId: String): ProfessionalLeaflet? {
        val document = dataSource.getLeaflet(medicationId, LeafletType.PROFESSIONAL.documentId)
        val jsonString = document?.leafletResume ?: return null
        val dto = GsonBuilder().defaultGSon().fromJson(jsonString, ProfessionalLeafletDTO::class.java)

        return dto.toDomain()
    }
}