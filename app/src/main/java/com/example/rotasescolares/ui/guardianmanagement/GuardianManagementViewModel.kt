package com.example.rotasescolares.ui.guardianmanagement

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.rotasescolares.data.AppDatabase
import com.example.rotasescolares.data.Guardian
import kotlinx.coroutines.launch

class GuardianManagementViewModel(application: Application) : AndroidViewModel(application) {

    private val guardianDao = AppDatabase.getDatabase(application).guardianDao()

    val allGuardians: LiveData<List<Guardian>> = guardianDao.getAllGuardians().asLiveData()

    fun deleteGuardian(guardian: Guardian) {
        viewModelScope.launch {
            guardianDao.delete(guardian)
        }
    }
}
