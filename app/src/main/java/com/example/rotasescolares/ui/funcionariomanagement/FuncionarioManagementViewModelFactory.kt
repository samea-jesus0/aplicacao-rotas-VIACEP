package com.example.rotasescolares.ui.funcionariomanagement

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class FuncionarioManagementViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FuncionarioManagementViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FuncionarioManagementViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
