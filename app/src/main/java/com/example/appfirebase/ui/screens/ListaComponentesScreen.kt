package com.example.appfirebase.ui.screens


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.appfirebase.data.model.Componente
import com.example.appfirebase.ui.theme.CyanAccent
import com.example.appfirebase.ui.theme.DarkSurface
import com.example.appfirebase.viewmodel.ComponenteViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListaComponentesScreen(
    vm: ComponenteViewModel,
    onNovo: () -> Unit,
    onEditar: (String) -> Unit,
    onDetalhes: (String) -> Unit
) {
    val lista by vm.lista.collectAsState()
    val carregando by vm.carregando.collectAsState()

    LaunchedEffect(Unit) { vm.carregar() }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("⚡ Componentes Eletrônicos") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = DarkSurface,
                    titleContentColor = CyanAccent
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onNovo,
                containerColor = CyanAccent,
                contentColor = androidx.compose.ui.graphics.Color.Black
            ) { Icon(Icons.Default.Add, "Novo") }
        }
    ) { padding ->
        Box(Modifier.fillMaxSize().padding(padding)) {
            if (carregando && lista.isEmpty()) {
                CircularProgressIndicator(Modifier.align(Alignment.Center))
            } else if (lista.isEmpty()) {
                Text("Nenhum componente cadastrado.",
                    Modifier.align(Alignment.Center))
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(lista) { c ->
                        ComponenteCard(
                            componente = c,
                            onClick = { onDetalhes(c.id) },
                            onEditar = { onEditar(c.id) },
                            onDeletar = { vm.deletar(c.id) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ComponenteCard(
    componente: Componente,
    onClick: () -> Unit,
    onEditar: () -> Unit,
    onDeletar: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = DarkSurface),
        onClick = onClick
    ) {
        Row(
            Modifier.padding(16.dp).fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(Modifier.weight(1f)) {
                Text(componente.nome, style = MaterialTheme.typography.titleLarge)
                Text("${componente.tipo.label} • ${componente.fabricante}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = CyanAccent)
                Text("Qtd: ${componente.quantidade} • R$ ${"%.2f".format(componente.preco)}",
                    style = MaterialTheme.typography.bodySmall)
            }
            IconButton(onClick = onEditar) {
                Icon(Icons.Default.Edit, "Editar", tint = CyanAccent)
            }
            IconButton(onClick = onDeletar) {
                Icon(Icons.Default.Delete, "Excluir", tint = MaterialTheme.colorScheme.error)
            }
        }
    }
}