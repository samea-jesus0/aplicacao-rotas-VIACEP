package com.example.rotasescolares.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.rotasescolares.data.StudentDao
import com.example.rotasescolares.ui.tripcontrol.TripControlViewModel

class ViewModelFactory(private val studentDao: StudentDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TripControlViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TripControlViewModel(studentDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
