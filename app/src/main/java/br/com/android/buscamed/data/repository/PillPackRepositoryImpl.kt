package br.com.android.buscamed.data.repository

import br.com.android.buscamed.data.datasource.remote.PillPackRemoteDataSource
import br.com.android.buscamed.data.mapper.toDomain
import br.com.android.buscamed.domain.model.capture.ExecutionMetadata
import br.com.android.buscamed.domain.model.pillpack.PillPack
import br.com.android.buscamed.domain.repository.PillPackRepository
import java.io.File
import javax.inject.Inject

/**
 * Implementação do repositório de cartelas de comprimidos.
 *
 * @property remoteDataSource Fonte de dados remota para comunicação com a API.
 */
class PillPackRepositoryImpl @Inject constructor(
    private val remoteDataSource: PillPackRemoteDataSource
) : PillPackRepository {

    override suspend fun processText(text: String, imageFile: File, metadata: ExecutionMetadata): Result<PillPack> {
        return try {
            val responseDto = remoteDataSource.processPillPackText(text, imageFile, metadata)
            Result.success(responseDto.toDomain())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun processImage(text: String, imageFile: File, metadata: ExecutionMetadata): Result<PillPack> {
        return try {
            val responseDto = remoteDataSource.processPillPackImage(text, imageFile, metadata)
            Result.success(responseDto.toDomain())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}