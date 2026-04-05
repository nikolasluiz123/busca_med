package br.com.android.buscamed.data.repository

import br.com.android.buscamed.data.datasource.firestore.MedicationDataSource
import br.com.android.buscamed.data.mapper.toDomain
import br.com.android.buscamed.domain.model.medication.AnvisaMedication
import br.com.android.buscamed.domain.repository.MedicationRepository
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
}