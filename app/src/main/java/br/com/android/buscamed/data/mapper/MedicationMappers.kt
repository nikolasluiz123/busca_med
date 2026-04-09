package br.com.android.buscamed.data.mapper

import br.com.android.buscamed.data.datasource.remote.dto.leaflet.PatientLeafletDTO
import br.com.android.buscamed.data.datasource.remote.dto.leaflet.ProfessionalLeafletDTO
import br.com.android.buscamed.data.document.AnvisaMedicationDocument
import br.com.android.buscamed.domain.model.medication.AnvisaMedication
import br.com.android.buscamed.domain.model.medication.leaflet.PatientLeaflet
import br.com.android.buscamed.domain.model.medication.leaflet.ProfessionalLeaflet

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
        stripe = this.stripe,
        hasLeaflet = this.hasLeaflet,
        hasLeafletPatientResume = this.hasLeafletPatientResume,
        hasLeafletProfessionalResume = this.hasLeafletProfessionalResume
    )
}

fun PatientLeafletDTO.toDomain(): PatientLeaflet {
    return PatientLeaflet(
        commonSideEffects = this.commonSideEffects,
        contraindications = this.contraindications,
        howToUse = this.howToUse,
        indications = this.indications,
        interactionsToAvoid = this.interactionsToAvoid,
        mechanismOfAction = this.mechanismOfAction,
        missedDose = this.missedDose,
        precautionsAndWarnings = this.precautionsAndWarnings
    )
}

fun ProfessionalLeafletDTO.toDomain(): ProfessionalLeaflet {
    return ProfessionalLeaflet(
        adverseReactions = this.adverseReactions,
        clinicalWarnings = this.clinicalWarnings,
        dosageAdjustments = this.dosageAdjustments,
        drugInteractions = this.drugInteractions,
        indicationsAndSpectrum = this.indicationsAndSpectrum,
        labTestInterferences = this.labTestInterferences,
        pharmacologicalProperties = this.pharmacologicalProperties
    )
}