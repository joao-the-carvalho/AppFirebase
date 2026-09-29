package com.example.appfirebase.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.example.appfirebase.data.model.Componente
import com.example.appfirebase.data.model.TipoComponente
import kotlinx.coroutines.tasks.await

class ComponenteRepository {
    private val db = FirebaseFirestore.getInstance()
    private val collection = db.collection("componentes")

    suspend fun listar(): List<Componente> {
        return collection.get().await().documents.mapNotNull { doc ->
            val data = doc.data ?: return@mapNotNull null

            // 1. Pega a string do banco
            val tipoString = data["tipo"] as? String ?: ""

            // 2. Tenta converter para Enum. Se falhar (ex: string vazia ""), usa PLACA como padrão
            val tipoSeguro = try {
                TipoComponente.valueOf(tipoString)
            } catch (e: IllegalArgumentException) {
                TipoComponente.PLACA
            }

            Componente(
                id = doc.id,
                nome = data["nome"] as? String ?: "Sem nome",
                tipo = tipoSeguro,
                fabricante = data["fabricante"] as? String ?: "",
                modelo = data["modelo"] as? String ?: "",
                quantidade = (data["quantidade"] as? Long)?.toInt() ?: 0,
                preco = (data["preco"] as? Double) ?: 0.0,
                descricao = data["descricao"] as? String ?: "",
                tensao = data["tensao"] as? String ?: "",
                conectores = data["conectores"] as? String ?: "",
                impedancia = data["impedancia"] as? String ?: "",
                comprimento = data["comprimento"] as? String ?: ""
            )
        }
    }

    suspend fun buscar(id: String): Componente? {
        val doc = collection.document(id).get().await()
        val data = doc.data ?: return null

        val tipoString = data["tipo"] as? String ?: ""
        val tipoSeguro = try {
            TipoComponente.valueOf(tipoString)
        } catch (e: IllegalArgumentException) {
            TipoComponente.PLACA
        }

        return Componente(
            id = doc.id,
            nome = data["nome"] as? String ?: "Sem nome",
            tipo = tipoSeguro,
            fabricante = data["fabricante"] as? String ?: "",
            modelo = data["modelo"] as? String ?: "",
            quantidade = (data["quantidade"] as? Long)?.toInt() ?: 0,
            preco = (data["preco"] as? Double) ?: 0.0,
            descricao = data["descricao"] as? String ?: "",
            tensao = data["tensao"] as? String ?: "",
            conectores = data["conectores"] as? String ?: "",
            impedancia = data["impedancia"] as? String ?: "",
            comprimento = data["comprimento"] as? String ?: ""
        )
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

    // Garante que o Enum seja salvo como String (ex: "PLACA", "SENSOR")
    private fun Componente.toMap(): Map<String, Any?> = mapOf(
        "nome" to nome,
        "tipo" to tipo.name, // <-- Aqui salva como String correta
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