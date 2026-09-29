package com.example.appfirebase.ui.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.appfirebase.ui.screens.*
import com.example.appfirebase.viewmodel.ComponenteViewModel

@Composable
fun AppNavGraph() {
    val navController = rememberNavController()
    val vm: ComponenteViewModel = viewModel()

    NavHost(navController, startDestination = "lista") {
        composable("lista") {
            ListaComponentesScreen(
                vm = vm,
                onNovo = { navController.navigate("cadastro") },
                onEditar = { id -> navController.navigate("cadastro/$id") },
                onDetalhes = { id -> navController.navigate("detalhes/$id") }
            )
        }
        composable("cadastro/{id}?") { backStack ->
            val id = backStack.arguments?.getString("id")
            CadastroComponenteScreen(vm, id) { navController.popBackStack() }
        }
        composable("cadastro") {
            CadastroComponenteScreen(vm, null) { navController.popBackStack() }
        }
        composable("detalhes/{id}") { backStack ->
            val id = backStack.arguments?.getString("id") ?: ""
            DetalhesComponenteScreen(vm, id) { navController.popBackStack() }
        }
    }
}