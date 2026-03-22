package br.com.android.buscamed.data.datasource.firestore.core

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference

fun CollectionReference.documentOrNew(id: String?): DocumentReference {
    return id?.let(this::document) ?: this.document()
}