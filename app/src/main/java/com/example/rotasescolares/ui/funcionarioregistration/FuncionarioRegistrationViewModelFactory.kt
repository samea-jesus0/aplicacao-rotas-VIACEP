package com.example.rotasescolares.ui.funcionarioregistration

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class FuncionarioRegistrationViewModelFactory(
    private val application: Application,
    private val funcionarioId: Long
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FuncionarioRegistrationViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FuncionarioRegistrationViewModel(application, funcionarioId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
