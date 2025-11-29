package com.example.rotasescolares.ui.schoolregistration

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class SchoolRegistrationViewModelFactory(
    private val application: Application,
    private val schoolId: Long
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SchoolRegistrationViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SchoolRegistrationViewModel(application, schoolId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
