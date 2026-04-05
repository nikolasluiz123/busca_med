package br.com.android.buscamed.domain.usecase.medication

import android.util.Log
import br.com.android.buscamed.domain.core.validation.GeneralValidationError
import br.com.android.buscamed.domain.core.validation.UseCaseResult
import br.com.android.buscamed.domain.core.validation.ValidationError
import br.com.android.buscamed.domain.model.medication.AnvisaMedication
import br.com.android.buscamed.domain.repository.MedicationRepository
import br.com.android.buscamed.domain.usecase.medication.enumeration.BarcodeGeneralErrorType
import br.com.android.buscamed.injection.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Caso de uso responsável por coordenar a busca de medicamentos a partir de um código de barras.
 *
 * @property repository Repositório para acesso aos dados de medicamentos.
 * @property dispatcher O dispatcher de corrotina utilizado para garantir a execução em uma thread de IO.
 */
class SearchMedicationByBarcodeUseCase @Inject constructor(
    private val repository: MedicationRepository,
    @param:IoDispatcher private val dispatcher: CoroutineDispatcher
) {

    /**
     * Executa a busca de medicamentos.
     *
     * @param barcode A sequência de caracteres que representa o código de barras.
     * @return O resultado da operação contendo a lista de medicamentos ou uma lista de erros de validação.
     */
    suspend operator fun invoke(barcode: String): UseCaseResult<List<AnvisaMedication>> = withContext(dispatcher) {
        val validationErrors = mutableListOf<ValidationError>()

        if (barcode.isBlank() || barcode.length < 8) {
            validationErrors.add(GeneralValidationError(BarcodeGeneralErrorType.INVALID_BARCODE))
            return@withContext UseCaseResult.Error(validationErrors)
        }

        try {
            Log.d("SearchMedicationByBarcodeUseCase", "Buscando medicamentos pelo EAN: $barcode")

            val medications = repository.getMedicationsByEan(barcode)

            if (medications.isEmpty()) {
                validationErrors.add(GeneralValidationError(BarcodeGeneralErrorType.MEDICATION_NOT_FOUND))
                return@withContext UseCaseResult.Error(validationErrors)
            }

            Log.d("SearchMedicationByBarcodeUseCase", "Medicamentos encontrados: ${medications.joinToString { it.productName }}")

            return@withContext UseCaseResult.Success(medications)
        } catch (e: Exception) {
            validationErrors.add(GeneralValidationError(BarcodeGeneralErrorType.UNKNOWN_ERROR, e))
            return@withContext UseCaseResult.Error(validationErrors)
        }
    }
}