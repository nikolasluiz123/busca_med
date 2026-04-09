package br.com.android.buscamed.data.document

import com.google.firebase.firestore.DocumentId

/**
 * Representa o documento de um medicamento da ANVISA armazenado no banco de dados Firestore.
 * * Mapeia os campos exatos contidos na coleção, permitindo a desserialização automática
 * pelo SDK do Firebase.
 *
 * @property id Identificador único do documento gerado pelo Firestore.
 * @property activeIngredients Lista de princípios ativos do medicamento.
 * @property cnpj CNPJ do laboratório fabricante.
 * @property laboratory Nome do laboratório fabricante.
 * @property ean1 Código de barras EAN primário.
 * @property ean2 Código de barras EAN secundário.
 * @property ean3 Código de barras EAN terciário.
 * @property productName Nome comercial do produto.
 * @property presentation Descrição da apresentação (ex: formato, miligramas, quantidade).
 * @property therapeuticClass Classe terapêutica a qual o medicamento pertence.
 * @property productType Tipo de produto (ex: Similar, Genérico, Novo).
 * @property isHospitalRestriction Flag que indica se o medicamento possui restrição de uso exclusivo hospitalar.
 * @property stripe Classificação da tarja (ex: Vermelha, Preta, Sem Tarja).
 * @property hasLeaflet Flag que indica se o medicamento possui bula.
 * @property hasLeafletPatientResume Flag que indica se o medicamento possui bula do paciente resumida.
 * @property hasLeafletProfessionalResume Flag que indica se o medicamento possui bula do profissional resumida.
 */
data class AnvisaMedicationDocument(
    @DocumentId
    override val id: String? = null,
    val activeIngredients: List<String> = emptyList(),
    val cnpj: String = "",
    val laboratory: String = "",
    val ean1: String? = null,
    val ean2: String? = null,
    val ean3: String? = null,
    val productName: String = "",
    val presentation: String = "",
    val therapeuticClass: String = "",
    val productType: String = "",
    val isHospitalRestriction: Boolean = false,
    val stripe: String = "",
    val hasLeaflet: Boolean = false,
    val hasLeafletPatientResume: Boolean = false,
    val hasLeafletProfessionalResume: Boolean = false
) : FirestoreDocument()