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

    private val collectionName = "medications"

    override suspend fun getMedicationsByEan(ean: String): List<AnvisaMedicationDocument> {
        val querySnapshot = firestore.collection(collectionName)
            .where(
                Filter.or(
                    Filter.equalTo(AnvisaMedicationDocument::ean1.name, ean),
                    Filter.equalTo(AnvisaMedicationDocument::ean2.name, ean),
                    Filter.equalTo(AnvisaMedicationDocument::ean3.name, ean)
                )
            )
            .get()
            .await()

        return querySnapshot.documents.mapNotNull { documentSnapshot ->
            documentSnapshot.toObject(AnvisaMedicationDocument::class.java)
        }
    }
}