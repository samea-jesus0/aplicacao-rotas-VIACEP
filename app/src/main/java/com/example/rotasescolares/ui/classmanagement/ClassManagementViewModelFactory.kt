package com.example.rotasescolares.ui.classmanagement

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ClassManagementViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ClassManagementViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ClassManagementViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
