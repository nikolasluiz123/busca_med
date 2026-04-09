package br.com.android.buscamed.domain.model.medication.leaflet

data class PatientLeaflet(
    val commonSideEffects: List<String>,
    val contraindications: List<String>,
    val howToUse: String,
    val indications: List<String>,
    val interactionsToAvoid: List<String>,
    val mechanismOfAction: String,
    val missedDose: String,
    val precautionsAndWarnings: List<String>
)