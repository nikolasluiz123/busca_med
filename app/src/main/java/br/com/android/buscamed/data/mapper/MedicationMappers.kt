package br.com.android.buscamed.data.mapper

import br.com.android.buscamed.data.document.AnvisaMedicationDocument
import br.com.android.buscamed.domain.model.medication.AnvisaMedication

/**
 * Converte um documento do Firestore [AnvisaMedicationDocument] em uma entidade de domínio [AnvisaMedication].
 *
 * @return A entidade de domínio correspondente.
 */
fun AnvisaMedicationDocument.toDomain(): AnvisaMedication {
    return AnvisaMedication(
        id = this.id.orEmpty(),
        activeIngredients = this.activeIngredients,
        cnpj = this.cnpj,
        laboratory = this.laboratory,
        ean1 = this.ean1,
        ean2 = this.ean2,
        ean3 = this.ean3,
        productName = this.productName,
        presentation = this.presentation,
        therapeuticClass = this.therapeuticClass,
        productType = this.productType,
        isHospitalRestriction = this.isHospitalRestriction,
        stripe = this.stripe
    )
}