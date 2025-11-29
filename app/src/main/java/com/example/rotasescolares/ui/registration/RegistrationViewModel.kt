package com.example.rotasescolares.ui.registration

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.rotasescolares.data.AppDatabase
import com.example.rotasescolares.data.Usuario
import kotlinx.coroutines.launch
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

class RegistrationViewModel(application: Application) : AndroidViewModel(application) {

    private val usuarioDao = AppDatabase.getDatabase(application).usuarioDao()

    private val _registrationResult = MutableLiveData<Boolean>()
    val registrationResult: LiveData<Boolean> = _registrationResult

    fun registerUser(nome: String, cpf: String, telefone: String, email: String, placaVan: String, senha: String) {
        viewModelScope.launch {
            val existingUser = usuarioDao.getUsuarioPorEmail(email)
            if (existingUser != null) {
                _registrationResult.postValue(false) // User already exists
                return@launch
            }

            try {
                val senhaCriptografada = MessageDigest.getInstance("SHA-256")
                    .digest(senha.toByteArray())
                    .fold("") { str, it -> str + "%02x".format(it) }

                val novoUsuario = Usuario(
                    nome = nome,
                    cpf = cpf,
                    telefone = telefone,
                    email = email,
                    placaVan = placaVan,
                    senhaCriptografada = senhaCriptografada
                )
                
                usuarioDao.insert(novoUsuario)
                _registrationResult.postValue(true)
            } catch (e: NoSuchAlgorithmException) {
                // Log the error and handle it gracefully
                _registrationResult.postValue(false)
            }
        }
    }
}
