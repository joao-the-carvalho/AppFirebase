package com.example.appfirebase.data.model

data class Componente(
    val id: String = "",
    val nome: String = "",
    val tipo: TipoComponente = TipoComponente.PLACA,
    val fabricante: String = "",
    val modelo: String = "",
    val quantidade: Int = 0,
    val preco: Double = 0.0,
    val descricao: String = "",
    // Campos específicos opcionais
    val tensao: String = "",          // ex: 5V, 12V
    val conectores: String = "",      // ex: USB-C, JST
    val impedancia: String = "",      // amplificadores
    val comprimento: String = ""      // cabos
)