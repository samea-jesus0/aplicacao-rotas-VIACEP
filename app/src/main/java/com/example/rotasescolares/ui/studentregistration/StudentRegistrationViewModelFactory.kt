package com.example.rotasescolares.ui.studentregistration

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class StudentRegistrationViewModelFactory(
    private val application: Application,
    private val studentId: Long
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StudentRegistrationViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return StudentRegistrationViewModel(application, studentId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
