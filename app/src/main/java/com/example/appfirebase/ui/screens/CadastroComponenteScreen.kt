package com.example.appfirebase.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.appfirebase.data.model.Componente
import com.example.appfirebase.data.model.TipoComponente
import com.example.appfirebase.viewmodel.ComponenteViewModel
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CadastroComponenteScreen(
    vm: ComponenteViewModel,
    componenteId: String?,
    onVoltar: () -> Unit
) {
    var nome by remember { mutableStateOf("") }
    var tipo by remember { mutableStateOf(TipoComponente.PLACA) }
    var fabricante by remember { mutableStateOf("") }
    var modelo by remember { mutableStateOf("") }
    var quantidade by remember { mutableStateOf("") }
    var preco by remember { mutableStateOf("") }
    var descricao by remember { mutableStateOf("") }
    var tensao by remember { mutableStateOf("") }
    var conectores by remember { mutableStateOf("") }
    var impedancia by remember { mutableStateOf("") }
    var comprimento by remember { mutableStateOf("") }

    // Carrega dados se for edição
    LaunchedEffect(componenteId) {
        if (!componenteId.isNullOrBlank()) {
            vm.buscar(componenteId)?.let { c ->
                nome = c.nome; tipo = c.tipo; fabricante = c.fabricante
                modelo = c.modelo; quantidade = c.quantidade.toString()
                preco = c.preco.toString(); descricao = c.descricao
                tensao = c.tensao; conectores = c.conectores
                impedancia = c.impedancia; comprimento = c.comprimento
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (componenteId.isNullOrBlank()) "Novo Componente" else "Editar") },
                navigationIcon = {
                    IconButton(onClick = onVoltar) {
                        Icon(Icons.Default.ArrowBack, "Voltar")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            Modifier.padding(padding).padding(16.dp).verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(nome, { nome = it }, label = { Text("Nome *") },
                modifier = Modifier.fillMaxWidth())

            // Dropdown de tipo
            var expanded by remember { mutableStateOf(false) }
            ExposedDropdownMenuBox(expanded, { expanded = it }) {
                OutlinedTextField(
                    value = tipo.label,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Tipo") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
                    modifier = Modifier.menuAnchor().fillMaxWidth()
                )
                ExposedDropdownMenu(expanded, { expanded = false }) {
                    TipoComponente.values().forEach { t ->
                        DropdownMenuItem(text = { Text(t.label) }, onClick = {
                            tipo = t; expanded = false
                        })
                    }
                }
            }

            OutlinedTextField(fabricante, { fabricante = it }, label = { Text("Fabricante") },
                modifier = Modifier.fillMaxWidth())
            OutlinedTextField(modelo, { modelo = it }, label = { Text("Modelo") },
                modifier = Modifier.fillMaxWidth())
            OutlinedTextField(
                value = quantidade,
                onValueChange = { quantidade = it },
                label = { Text("Quantidade") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            OutlinedTextField(preco, { preco = it }, label = { Text("Preço (R$)") },
                modifier = Modifier.fillMaxWidth())
            OutlinedTextField(descricao, { descricao = it }, label = { Text("Descrição") },
                modifier = Modifier.fillMaxWidth(), minLines = 3)

            // Campos específicos por tipo
            when (tipo) {
                TipoComponente.PLACA, TipoComponente.SENSOR ->
                    OutlinedTextField(tensao, { tensao = it }, label = { Text("Tensão (ex: 5V)") },
                        modifier = Modifier.fillMaxWidth())
                TipoComponente.SENSOR ->
                    OutlinedTextField(conectores, { conectores = it },
                        label = { Text("Conectores") }, modifier = Modifier.fillMaxWidth())
                TipoComponente.AMPLIFICADOR ->
                    OutlinedTextField(impedancia, { impedancia = it },
                        label = { Text("Impedância (Ω)") }, modifier = Modifier.fillMaxWidth())
                TipoComponente.CABO ->
                    OutlinedTextField(comprimento, { comprimento = it },
                        label = { Text("Comprimento (m)") }, modifier = Modifier.fillMaxWidth())
            }

            Spacer(Modifier.height(8.dp))

            Button(
                onClick = {
                    val c = Componente(
                        id = componenteId ?: "",
                        nome = nome, tipo = tipo, fabricante = fabricante,
                        modelo = modelo,
                        quantidade = quantidade.toIntOrNull() ?: 0,
                        preco = preco.toDoubleOrNull() ?: 0.0,
                        descricao = descricao, tensao = tensao,
                        conectores = conectores, impedancia = impedancia,
                        comprimento = comprimento
                    )
                    vm.salvar(c) { onVoltar() }
                },
                modifier = Modifier.fillMaxWidth()
            ) { Text("Salvar") }
        }
    }
}