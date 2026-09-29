package com.example.appfirebase.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appfirebase.data.model.Componente
import com.example.appfirebase.data.repository.ComponenteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ComponenteViewModel : ViewModel() {
    private val repo = ComponenteRepository()

    private val _lista = MutableStateFlow<List<Componente>>(emptyList())
    val lista: StateFlow<List<Componente>> = _lista

    private val _carregando = MutableStateFlow(false)
    val carregando: StateFlow<Boolean> = _carregando

    fun carregar() {
        viewModelScope.launch {
            _carregando.value = true
            _lista.value = repo.listar()
            _carregando.value = false
        }
    }

    fun salvar(c: Componente, onSuccess: () -> Unit) {
        viewModelScope.launch {
            _carregando.value = true
            if (c.id.isBlank()) repo.criar(c) else repo.atualizar(c)
            carregar()
            _carregando.value = false
            onSuccess()
        }
    }

    fun deletar(id: String) {
        viewModelScope.launch {
            repo.deletar(id)
            carregar()
        }
    }

    suspend fun buscar(id: String): Componente? = repo.buscar(id)
}