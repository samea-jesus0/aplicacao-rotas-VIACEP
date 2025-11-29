package com.example.rotasescolares.ui.funcionarioregistration

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.rotasescolares.data.AppDatabase
import com.example.rotasescolares.data.Funcionario
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class FuncionarioRegistrationViewModel(
    application: Application,
    private val funcionarioId: Long
) : AndroidViewModel(application) {

    private val funcionarioDao = AppDatabase.getDatabase(application).funcionarioDao()

    private val _funcionario = MutableLiveData<Funcionario?>()
    val funcionario: LiveData<Funcionario?> = _funcionario

    private val _funcionarioSaved = MutableLiveData<Boolean>()
    val funcionarioSaved: LiveData<Boolean> = _funcionarioSaved

    init {
        if (funcionarioId != -1L) {
            viewModelScope.launch {
                _funcionario.value = funcionarioDao.getFuncionarioById(funcionarioId).first()
            }
        }
    }

    fun saveFuncionario(
        nome: String,
        cpf: String,
        telefone: String,
        email: String?,
        cargo: String,
        turno: String
    ) {
        viewModelScope.launch {
            val funcionarioToSave = _funcionario.value?.copy(
                nome = nome,
                cpf = cpf,
                telefone = telefone,
                email = email,
                cargo = cargo,
                turno = turno
            ) ?: Funcionario(
                nome = nome,
                cpf = cpf,
                telefone = telefone,
                email = email,
                cargo = cargo,
                turno = turno
            )

            if (funcionarioId == -1L) {
                funcionarioDao.insert(funcionarioToSave)
            } else {
                funcionarioDao.update(funcionarioToSave)
            }
            _funcionarioSaved.postValue(true)
        }
    }

    fun onFuncionarioSaved() {
        _funcionarioSaved.value = false
    }
}
