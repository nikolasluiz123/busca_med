package br.com.android.buscamed.data.document

import com.google.firebase.firestore.DocumentId
import java.util.Date

/**
 * Representa o documento de bula (leaflet) de um medicamento armazenado na subcoleção do Firestore.
 *
 * @property id Identificador único do documento (ex: PATIENT ou PROFESSIONAL).
 * @property leafletStoragePath Caminho do arquivo físico da bula armazenado no Storage, se houver.
 * @property leafletResume String contendo o JSON com os dados estruturados do resumo da bula.
 * @property leafletResumeCreatedAt Data de criação do registro da bula.
 * @property leafletResumeUpdatedAt Data da última atualização do registro da bula.
 */
data class AnvisaMedicationLeafletDocument(
    @DocumentId
    override val id: String? = null,
    val leafletStoragePath: String? = null,
    val leafletResume: String? = null,
    val leafletResumeCreatedAt: Date? = null,
    val leafletResumeUpdatedAt: Date? = null
) : FirestoreDocument()