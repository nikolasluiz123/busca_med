package br.com.android.buscamed.domain.model.medication.leaflet

data class ProfessionalLeaflet(
    val adverseReactions: List<String>,
    val clinicalWarnings: List<String>,
    val dosageAdjustments: List<String>,
    val drugInteractions: List<String>,
    val indicationsAndSpectrum: List<String>,
    val labTestInterferences: List<String>,
    val pharmacologicalProperties: String
)