package br.com.android.buscamed.data.datasource.firestore.core

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference

/**
 * Retorna uma referência a um documento existente ou cria uma nova referência.
 * 
 * Se um [id] for fornecido, a função retorna a referência ao documento com esse ID.
 * Caso contrário, cria uma referência a um novo documento com ID gerado automaticamente.
 * 
 * @param id Identificador opcional do documento.
 * @return Uma [DocumentReference] para o Firestore.
 */
fun CollectionReference.documentOrNew(id: String?): DocumentReference {
    return id?.let(this::document) ?: this.document()
}
