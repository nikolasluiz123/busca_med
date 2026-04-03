package br.com.android.buscamed.data.repository

import br.com.android.buscamed.data.datasource.remote.PrescriptionRemoteDataSource
import br.com.android.buscamed.data.mapper.toDomain
import br.com.android.buscamed.domain.model.prescription.Prescription
import br.com.android.buscamed.domain.repository.PrescriptionRepository
import java.io.File
import javax.inject.Inject

/**
 * Implementação do repositório de prescrições médicas.
 *
 * @property remoteDataSource Fonte de dados remota para comunicação com a API.
 */
class PrescriptionRepositoryImpl @Inject constructor(
    private val remoteDataSource: PrescriptionRemoteDataSource
) : PrescriptionRepository {

    override suspend fun processText(text: String): Result<Prescription> {
        return try {
            val responseDto = remoteDataSource.processPrescriptionText(text)
            Result.success(responseDto.toDomain())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun processImage(imageFile: File): Result<Prescription> {
        return try {
            val responseDto = remoteDataSource.processPrescriptionImage(imageFile)
            Result.success(responseDto.toDomain())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}