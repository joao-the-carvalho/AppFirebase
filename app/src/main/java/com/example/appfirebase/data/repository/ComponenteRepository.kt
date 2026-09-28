package com.example.appfirebase.data.repository

import com.example.appfirebase.data.model.Componente
import com.google.firebase.firestore.FirebaseFirestore
import com.example.appfirebase.data.model.TipoComponente
import kotlinx.coroutines.tasks.await

class ComponenteRepository {
    private val db = FirebaseFirestore.getInstance()
    private val collection = db.collection("componentes")

    suspend fun listar(): List<Componente> {
        return collection.get().await().documents.mapNotNull { doc ->
            doc.toObject(Componente::class.java)?.copy(id = doc.id)
        }
    }

    suspend fun buscar(id: String): Componente? {
        return collection.document(id).get().await()
            .toObject(Componente::class.java)?.copy(id = id)
    }

    suspend fun criar(c: Componente): String {
        val doc = collection.document()
        val novo = c.copy(id = doc.id)
        doc.set(novo.toMap()).await()
        return doc.id
    }

    suspend fun atualizar(c: Componente) {
        collection.document(c.id).set(c.toMap()).await()
    }

    suspend fun deletar(id: String) {
        collection.document(id).delete().await()
    }

    // Converte para Map (Firestore precisa de tipos simples)
    private fun Componente.toMap(): Map<String, Any?> = mapOf(
        "nome" to nome,
        "tipo" to tipo.name,
        "fabricante" to fabricante,
        "modelo" to modelo,
        "quantidade" to quantidade,
        "preco" to preco,
        "descricao" to descricao,
        "tensao" to tensao,
        "conectores" to conectores,
        "impedancia" to impedancia,
        "comprimento" to comprimento
    )
}