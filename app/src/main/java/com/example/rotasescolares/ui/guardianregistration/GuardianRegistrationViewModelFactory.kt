package com.example.rotasescolares.ui.guardianregistration

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class GuardianRegistrationViewModelFactory(
    private val application: Application,
    private val guardianId: Long
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GuardianRegistrationViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return GuardianRegistrationViewModel(application, guardianId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
