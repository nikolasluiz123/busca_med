package br.com.android.buscamed.data.mapper

import br.com.android.buscamed.data.datasource.remote.dto.prescription.response.DurationDTO
import br.com.android.buscamed.data.datasource.remote.dto.prescription.response.FrequencyDTO
import br.com.android.buscamed.data.datasource.remote.dto.prescription.response.PrescriptionMedicationDTO
import br.com.android.buscamed.data.datasource.remote.dto.prescription.response.PrescriptionResponseDTO
import br.com.android.buscamed.data.datasource.remote.dto.generic.response.ValueUnitDTO
import br.com.android.buscamed.domain.model.prescription.*
import br.com.android.buscamed.domain.model.generic.ValueUnit

fun PrescriptionResponseDTO.toDomain(): Prescription {
    return Prescription(
        medications = this.medicamentos.map { it.toDomain() }
    )
}

fun PrescriptionMedicationDTO.toDomain(): PrescriptionMedication {
    return PrescriptionMedication(
        name = this.nome,
        presentationDosage = this.apresentacaoDosagem?.toDomain(),
        dose = this.dose?.toDomain(),
        frequency = this.frequencia?.toDomain(),
        duration = this.duracao?.toDomain(),
        totalPrescribedQuantity = this.quantidadeTotalPrescrita?.toDomain()
    )
}

fun ValueUnitDTO.toDomain(): ValueUnit {
    return ValueUnit(
        value = this.valor,
        unit = this.unidade
    )
}

fun FrequencyDTO.toDomain(): Frequency {
    return Frequency(
        interval = this.intervalo,
        unit = this.unidade,
        guidanceText = this.textoOrientacao
    )
}

fun DurationDTO.toDomain(): Duration {
    return Duration(
        value = this.valor,
        unit = this.unidade,
        isContinuousUse = this.usoContinuo
    )
}