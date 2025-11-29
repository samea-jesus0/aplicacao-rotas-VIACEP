package com.example.rotasescolares.ui.studentmanagement

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class StudentManagementViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StudentManagementViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return StudentManagementViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
