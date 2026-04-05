package br.com.android.buscamed.data.mapper

import br.com.android.buscamed.data.datasource.remote.dto.pillpack.response.PillPackComponentDTO
import br.com.android.buscamed.data.datasource.remote.dto.pillpack.response.PillPackResponseDTO
import br.com.android.buscamed.data.datasource.remote.dto.pillpack.response.PillPackUsageDTO
import br.com.android.buscamed.domain.model.pillpack.PillPack
import br.com.android.buscamed.domain.model.pillpack.PillPackComponent
import br.com.android.buscamed.domain.model.pillpack.PillPackUsage

fun PillPackResponseDTO.toDomain(): PillPack {
    return PillPack(
        medicationName = this.nomeMedicamento,
        components = this.componentes.map { it.toDomain() },
        usage = this.uso?.toDomain(),
        indications = this.indicacoes,
        expirationDate = this.dataValidade,
        batch = this.lote
    )
}

fun PillPackComponentDTO.toDomain(): PillPackComponent {
    return PillPackComponent(
        activeIngredient = this.principioAtivo,
        dosageValue = this.dosagemValor,
        dosageUnit = this.dosagemUnidade
    )
}

fun PillPackUsageDTO.toDomain(): PillPackUsage {
    return PillPackUsage(
        administrationRoutes = this.viasAdministracao,
        ageRestrictions = this.restricoesIdade
    )
}