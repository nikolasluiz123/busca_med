package br.com.android.buscamed.data.datasource.firestore.core

import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

/**
 * Classe base abstrata para todos os DataSources que utilizam o Cloud Firestore.
 *
 * Centraliza o acesso à instância do banco de dados e provê utilitários comuns
 * para operações de baixo nível com o Firebase.
 *
 * @property db Instância do [FirebaseFirestore] injetada.
 */
abstract class FirestoreDataSource(
    protected val db: FirebaseFirestore
) {
    /**
     * Obtém o timestamp atual do servidor do Firebase.
     *
     * Esta função realiza uma operação temporária de escrita e leitura em uma
     * coleção dedicada ("serverTime") para garantir que o horário obtido seja
     * o horário exato do servidor do Google, evitando dependências do relógio local do dispositivo.
     *
     * @return O timestamp do servidor em milissegundos (Long).
     */
    protected suspend fun getServerTime(): Long {
        val dummyDocRef = db.collection("serverTime").document("timestamp")

        val data = mapOf("timestamp" to FieldValue.serverTimestamp())
        dummyDocRef.set(data).await()

        val snapshot = dummyDocRef.get().await()
        val serverTimestamp = snapshot.getTimestamp("timestamp")?.seconds!! * 1000

        return serverTimestamp
    }
}
