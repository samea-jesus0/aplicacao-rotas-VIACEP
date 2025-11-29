package com.example.rotasescolares.ui.classregistration

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ClassRegistrationViewModelFactory(
    private val application: Application,
    private val classId: Long
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ClassRegistrationViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ClassRegistrationViewModel(application, classId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
