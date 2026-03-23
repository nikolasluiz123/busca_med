package br.com.android.buscamed.data.document

import com.google.firebase.firestore.DocumentId

/**
 * Modelo de dados que representa um usuário no Firestore.
 *
 * @property id O identificador único gerado pelo Firestore (mapeado via @DocumentId).
 * @property name Nome completo do usuário.
 * @property normalizedName Versão normalizada do nome para facilitar buscas.
 * @property email Endereço de e-mail do usuário.
 */
data class UserDocument(
    @DocumentId
    override val id: String? = null,
    val name: String = "",
    val normalizedName: String = "",
    val email: String = ""
): FirestoreDocument()
