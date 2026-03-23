package br.com.android.buscamed.data.datasource.firestore.core

import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

abstract class FirestoreDataSource(
    protected val db: FirebaseFirestore
) {
    protected suspend fun getServerTime(): Long {
        val dummyDocRef = db.collection("serverTime").document("timestamp")

        val data = mapOf("timestamp" to FieldValue.serverTimestamp())
        dummyDocRef.set(data).await()

        val snapshot = dummyDocRef.get().await()
        val serverTimestamp = snapshot.getTimestamp("timestamp")?.seconds!! * 1000

        return serverTimestamp
    }
}