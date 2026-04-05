package br.com.android.buscamed.data.datasource.firestore

import br.com.android.buscamed.data.document.AnvisaMedicationDocument
import com.google.firebase.firestore.Filter
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

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

    companion object {
        private const val COLLECTION_NAME = "anvisaMedications"
    }
}