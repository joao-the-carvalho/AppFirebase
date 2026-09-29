package com.example.appfirebase.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.appfirebase.data.model.Componente
import com.example.appfirebase.ui.theme.CyanAccent
import com.example.appfirebase.viewmodel.ComponenteViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetalhesComponenteScreen(
    vm: ComponenteViewModel,
    componenteId: String,
    onVoltar: () -> Unit
) {
    var componente by remember { mutableStateOf<Componente?>(null) }

    LaunchedEffect(componenteId) {
        componente = vm.buscar(componenteId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalhes") },
                navigationIcon = {
                    IconButton(onClick = onVoltar) {
                        Icon(Icons.Default.ArrowBack, "Voltar")
                    }
                }
            )
        }
    ) { padding ->
        componente?.let { c ->
            Column(Modifier.padding(padding).padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(c.nome, style = MaterialTheme.typography.headlineLarge)
                Text(c.tipo.label, color = CyanAccent)
                Divider()
                InfoRow("Fabricante", c.fabricante)
                InfoRow("Modelo", c.modelo)
                InfoRow("Quantidade", c.quantidade.toString())
                InfoRow("Preço", "R$ ${"%.2f".format(c.preco)}")
                InfoRow("Descrição", c.descricao)
                if (c.tensao.isNotBlank()) InfoRow("Tensão", c.tensao)
                if (c.conectores.isNotBlank()) InfoRow("Conectores", c.conectores)
                if (c.impedancia.isNotBlank()) InfoRow("Impedância", c.impedancia)
                if (c.comprimento.isNotBlank()) InfoRow("Comprimento", c.comprimento)
            }
        } ?: CircularProgressIndicator(Modifier.padding(32.dp))
    }
}

@Composable
fun InfoRow(label: String, value: String) {
    Column {
        Text(label, style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f))
        Text(value, style = MaterialTheme.typography.bodyLarge)
    }
}