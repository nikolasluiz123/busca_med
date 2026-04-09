package br.com.android.buscamed.domain.usecase.medication

import br.com.android.buscamed.domain.core.pagination.PaginatedResult
import br.com.android.buscamed.domain.core.validation.UseCaseResult
import br.com.android.buscamed.domain.model.medication.AnvisaMedication
import br.com.android.buscamed.domain.repository.MedicationRepository
import br.com.android.buscamed.injection.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetPaginatedMedicationsUseCase @Inject constructor(
    private val repository: MedicationRepository,
    @param:IoDispatcher private val dispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(
        limit: Int = 100,
        cursor: Any? = null
    ): UseCaseResult<PaginatedResult<AnvisaMedication>> = withContext(dispatcher) {
        val result = repository.getPaginatedMedications(limit, cursor)
        UseCaseResult.Success(result)
    }
}