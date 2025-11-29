package com.example.rotasescolares.ui.classmanagement

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.rotasescolares.data.AppDatabase
import com.example.rotasescolares.data.Turma
import com.example.rotasescolares.data.TurmaWithAlunos
import kotlinx.coroutines.launch

class ClassManagementViewModel(application: Application) : AndroidViewModel(application) {

    private val turmaDao = AppDatabase.getDatabase(application).turmaDao()

    val allTurmas: LiveData<List<TurmaWithAlunos>> = turmaDao.getTurmasWithAlunos().asLiveData()

    fun deleteTurma(turma: Turma) {
        viewModelScope.launch {
            turmaDao.delete(turma)
        }
    }
}
