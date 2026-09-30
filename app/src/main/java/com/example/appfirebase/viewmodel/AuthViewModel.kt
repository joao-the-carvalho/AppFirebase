package com.example.appfirebase.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {
    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    private val _isLoggedIn = MutableStateFlow(auth.currentUser != null)
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn

    fun register(nome: String, email: String, senha: String, onSuccess: () -> Unit) {
        if (nome.isBlank() || email.isBlank() || senha.isBlank()) {
            _errorMessage.value = "Preencha todos os campos"
            return
        }
        _isLoading.value = true
        _errorMessage.value = null

        auth.createUserWithEmailAndPassword(email, senha)
            .addOnCompleteListener { task ->
                _isLoading.value = false
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    user?.let {
                        // Salva o nome no Firestore
                        firestore.collection("usuarios").document(it.uid)
                            .set(mapOf("nome" to nome, "email" to email))
                    }
                    _isLoggedIn.value = true
                    onSuccess()
                } else {
                    _errorMessage.value = task.exception?.message ?: "Erro ao registrar"
                }
            }
    }

    fun login(email: String, senha: String, onSuccess: () -> Unit) {
        if (email.isBlank() || senha.isBlank()) {
            _errorMessage.value = "Preencha e-mail e senha"
            return
        }
        _isLoading.value = true
        _errorMessage.value = null

        auth.signInWithEmailAndPassword(email, senha)
            .addOnCompleteListener { task ->
                _isLoading.value = false
                if (task.isSuccessful) {
                    _isLoggedIn.value = true
                    onSuccess()
                } else {
                    _errorMessage.value = task.exception?.message ?: "E-mail ou senha inválidos"
                }
            }
    }

    fun loginWithGoogle(context: Context, onSuccess: () -> Unit) {
        _isLoading.value = true
        _errorMessage.value = null

        // ⚠️ SUBSTITUA PELO SEU WEB CLIENT ID DO FIREBASE CONSOLE
        val webClientId = "SEU_WEB_CLIENT_ID_AQUI.apps.googleusercontent.com"

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(webClientId)
            .requestEmail()
            .build()

        val googleSignInClient = GoogleSignIn.getClient(context, gso)
        val signInIntent = googleSignInClient.signInIntent

        // Como estamos no ViewModel, precisamos de um contexto de Activity para iniciar o intent.
        // A maneira mais segura no Compose é passar o Intent para a UI tratar, mas para simplificar:
        // Vamos usar uma abordagem alternativa mais limpa para Compose (ver nota abaixo).
        _isLoading.value = false
        _errorMessage.value = "Use o botão na tela de Login que chama a Activity Result"
    }

    fun logout() {
        auth.signOut()
        _isLoggedIn.value = false
    }

    fun clearError() {
        _errorMessage.value = null
    }
}