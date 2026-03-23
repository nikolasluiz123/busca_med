package br.com.android.buscamed.data.datasource.firestore

import br.com.android.buscamed.data.datasource.firestore.core.FirestoreDataSource
import br.com.android.buscamed.data.datasource.firestore.core.documentOrNew
import br.com.android.buscamed.data.document.UserDocument
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UserFirestoreDataSource @Inject constructor(db: FirebaseFirestore) : FirestoreDataSource(db), UserDataSource {

    override suspend fun getById(id: String): UserDocument? {
        return db.collection(USER_COLLECTION_NAME)
            .document(id)
            .get()
            .await()
            .toObject(UserDocument::class.java)
    }

    override suspend fun save(user: UserDocument) {
        db.collection(USER_COLLECTION_NAME)
            .documentOrNew(user.id)
            .set(user)
            .await()
    }

    companion object {
        const val USER_COLLECTION_NAME = "users"
    }
}
