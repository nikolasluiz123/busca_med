package br.com.android.buscamed.data.datasource.remote.dto.leaflet

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProfessionalLeafletDTO(
    @SerializedName("adverse_reactions")
    @SerialName("adverse_reactions")
    val adverseReactions: List<String> = emptyList(),

    @SerializedName("clinical_warnings")
    @SerialName("clinical_warnings")
    val clinicalWarnings: List<String> = emptyList(),

    @SerializedName("dosage_adjustments")
    @SerialName("dosage_adjustments")
    val dosageAdjustments: List<String> = emptyList(),

    @SerializedName("drug_interactions")
    @SerialName("drug_interactions")
    val drugInteractions: List<String> = emptyList(),

    @SerializedName("indications_and_spectrum")
    @SerialName("indications_and_spectrum")
    val indicationsAndSpectrum: List<String> = emptyList(),

    @SerializedName("lab_test_interferences")
    @SerialName("lab_test_interferences")
    val labTestInterferences: List<String> = emptyList(),

    @SerializedName("pharmacological_properties")
    @SerialName("pharmacological_properties")
    val pharmacologicalProperties: String = ""
)