package br.com.android.buscamed.data.datasource.remote.dto.leaflet

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PatientLeafletDTO(
    @SerializedName("common_side_effects")
    @SerialName("common_side_effects")
    val commonSideEffects: List<String> = emptyList(),

    @SerializedName("contraindications")
    @SerialName("contraindications")
    val contraindications: List<String> = emptyList(),

    @SerializedName("how_to_use")
    @SerialName("how_to_use")
    val howToUse: String = "",

    @SerializedName("indications")
    @SerialName("indications")
    val indications: List<String> = emptyList(),

    @SerializedName("interactions_to_avoid")
    @SerialName("interactions_to_avoid")
    val interactionsToAvoid: List<String> = emptyList(),

    @SerializedName("mechanism_of_action")
    @SerialName("mechanism_of_action")
    val mechanismOfAction: String = "",

    @SerializedName("missed_dose")
    @SerialName("missed_dose")
    val missedDose: String = "",

    @SerializedName("precautions_and_warnings")
    @SerialName("precautions_and_warnings")
    val precautionsAndWarnings: List<String> = emptyList()
)