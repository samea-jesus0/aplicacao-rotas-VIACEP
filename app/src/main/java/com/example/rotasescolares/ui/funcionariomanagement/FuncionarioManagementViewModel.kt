package com.example.rotasescolares.ui.funcionariomanagement

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.rotasescolares.data.AppDatabase
import com.example.rotasescolares.data.Funcionario
import kotlinx.coroutines.launch

class FuncionarioManagementViewModel(application: Application) : AndroidViewModel(application) {

    private val funcionarioDao = AppDatabase.getDatabase(application).funcionarioDao()

    val allFuncionarios: LiveData<List<Funcionario>> = funcionarioDao.getAllFuncionarios().asLiveData()

    fun deleteFuncionario(funcionario: Funcionario) {
        viewModelScope.launch {
            funcionarioDao.delete(funcionario)
        }
    }
}
