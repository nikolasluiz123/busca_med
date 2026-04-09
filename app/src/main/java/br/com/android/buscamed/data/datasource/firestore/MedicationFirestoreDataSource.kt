package br.com.android.buscamed.data.datasource.firestore

import br.com.android.buscamed.data.document.AnvisaMedicationDocument
import br.com.android.buscamed.data.document.AnvisaMedicationLeafletDocument
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Filter
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import kotlin.jvm.java

/**
 * Implementação da fonte de dados de medicamentos utilizando o Firebase Firestore.
 *
 * @property firestore Instância do cliente do Firebase Firestore.
 */
class MedicationFirestoreDataSource @Inject constructor(
    private val firestore: FirebaseFirestore
) : MedicationDataSource {

    override suspend fun getMedicationsByEan(ean: String): List<AnvisaMedicationDocument> {
        val querySnapshot = firestore.collection(COLLECTION_NAME)
            .whereEqualTo(AnvisaMedicationDocument::ean1.name, ean)
            .get()
            .await()

        return querySnapshot.documents.mapNotNull { documentSnapshot ->
            documentSnapshot.toObject(AnvisaMedicationDocument::class.java)
        }
    }

    override suspend fun getPaginatedMedications(
        limit: Int,
        cursorSnapshot: Any?
    ): Pair<List<AnvisaMedicationDocument>, Any?> {

        var baseQuery = firestore.collection(COLLECTION_NAME)
            .whereEqualTo(AnvisaMedicationDocument::hasLeaflet.name, true)
            .where(
                Filter.or(
                    Filter.equalTo(AnvisaMedicationDocument::hasLeafletPatientResume.name, true),
                    Filter.equalTo(AnvisaMedicationDocument::hasLeafletProfessionalResume.name, true)
                )
            )
            .orderBy(AnvisaMedicationDocument::productName.name, Query.Direction.ASCENDING)

        if (cursorSnapshot is DocumentSnapshot) {
            baseQuery = baseQuery.startAfter(cursorSnapshot)
        }

        val snapshot = baseQuery.limit(limit.toLong()).get().await()

        val documents = snapshot.documents.mapNotNull {
            it.toObject(AnvisaMedicationDocument::class.java)
        }

        val lastVisible = if (snapshot.size() > 0) snapshot.documents.last() else null

        return Pair(documents, lastVisible)
    }

    override suspend fun getLeaflet(medicationId: String, typeId: String): AnvisaMedicationLeafletDocument? {
        return firestore.collection(COLLECTION_NAME)
            .document(medicationId)
            .collection(LEAFLETS_SUBCOLLECTION)
            .document(typeId)
            .get()
            .await()
            .toObject(AnvisaMedicationLeafletDocument::class.java)
    }

    companion object {
        private const val COLLECTION_NAME = "anvisaMedications"
        private const val LEAFLETS_SUBCOLLECTION = "leaflets"
    }
}