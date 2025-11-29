package com.example.rotasescolares.ui.guardianmanagement

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class GuardianManagementViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GuardianManagementViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return GuardianManagementViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
