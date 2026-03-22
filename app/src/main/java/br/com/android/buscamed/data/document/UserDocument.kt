package br.com.android.buscamed.data.document

import com.google.firebase.firestore.DocumentId

data class UserDocument(
    @DocumentId
    override val id: String? = null,
    val name: String = "",
    val email: String = ""
): FirestoreDocument()
