package com.example.rotasescolares.ui.login

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.rotasescolares.data.AppDatabase
import kotlinx.coroutines.launch
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private val usuarioDao = AppDatabase.getDatabase(application).usuarioDao()

    private val _loginResult = MutableLiveData<Boolean>()
    val loginResult: LiveData<Boolean> = _loginResult

    fun loginUser(email: String, senha: String) {
        viewModelScope.launch {
            val usuario = usuarioDao.getUsuarioPorEmail(email)

            if (usuario != null) {
                try {
                    val senhaCriptografada = MessageDigest.getInstance("SHA-256")
                        .digest(senha.toByteArray())
                        .fold("") { str, it -> str + "%02x".format(it) }

                    if (usuario.senhaCriptografada == senhaCriptografada) {
                        _loginResult.postValue(true)
                    } else {
                        _loginResult.postValue(false)
                    }
                } catch (e: NoSuchAlgorithmException) {
                    // Log the error and handle it gracefully
                    _loginResult.postValue(false)
                }
            } else {
                _loginResult.postValue(false)
            }
        }
    }
}