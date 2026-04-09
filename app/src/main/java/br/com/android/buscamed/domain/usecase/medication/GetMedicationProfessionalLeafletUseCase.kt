package br.com.android.buscamed.domain.usecase.medication

import br.com.android.buscamed.domain.core.validation.GeneralValidationError
import br.com.android.buscamed.domain.core.validation.UseCaseResult
import br.com.android.buscamed.domain.model.medication.leaflet.PatientLeaflet
import br.com.android.buscamed.domain.model.medication.leaflet.ProfessionalLeaflet
import br.com.android.buscamed.domain.repository.MedicationRepository
import br.com.android.buscamed.domain.usecase.medication.enumeration.BarcodeGeneralErrorType
import br.com.android.buscamed.domain.usecase.medication.enumeration.MedicationLeafletGeneralErrorType
import br.com.android.buscamed.injection.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetMedicationProfessionalLeafletUseCase @Inject constructor(
    private val repository: MedicationRepository,
    @param:IoDispatcher private val dispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(medicationId: String): UseCaseResult<ProfessionalLeaflet> = withContext(dispatcher) {
        val leaflet = repository.getProfessionalLeaflet(medicationId)

        if (leaflet != null) {
            UseCaseResult.Success(leaflet)
        } else {
            UseCaseResult.Error(listOf(GeneralValidationError(MedicationLeafletGeneralErrorType.LEAFLET_NOT_FOUND)))
        }
    }
}