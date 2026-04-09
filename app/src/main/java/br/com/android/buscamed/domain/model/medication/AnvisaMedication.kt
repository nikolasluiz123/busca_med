package br.com.android.buscamed.domain.model.medication

/**
 * Entidade de domínio que representa um medicamento.
 *
 * @property id Identificador único.
 * @property activeIngredients Lista de princípios ativos.
 * @property cnpj CNPJ do laboratório responsável.
 * @property laboratory Nome do laboratório.
 * @property ean1 Código de barras EAN primário.
 * @property ean2 Código de barras EAN secundário.
 * @property ean3 Código de barras EAN terciário.
 * @property productName Nome comercial do medicamento.
 * @property presentation Apresentação física e dosagem.
 * @property therapeuticClass Classe terapêutica.
 * @property productType Tipo de produto.
 * @property isHospitalRestriction Indica se possui restrição de uso hospitalar.
 * @property stripe Classificação da tarja do medicamento.
 * @property hasLeaflet Indica se possui a bula.
 * @property hasLeafletPatientResume Indica se possui a bula do paciente resumida.
 * @property hasLeafletProfessionalResume Indica se possui a bula do profissional resumida.
 */
data class AnvisaMedication(
    val id: String,
    val activeIngredients: List<String>,
    val cnpj: String,
    val laboratory: String,
    val ean1: String?,
    val ean2: String?,
    val ean3: String?,
    val productName: String,
    val presentation: String,
    val therapeuticClass: String,
    val productType: String,
    val isHospitalRestriction: Boolean,
    val stripe: String,
    val hasLeaflet: Boolean,
    val hasLeafletPatientResume: Boolean,
    val hasLeafletProfessionalResume: Boolean
)