package com.example.rotasescolares.ui.schoolmanagement

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.rotasescolares.data.AppDatabase
import com.example.rotasescolares.data.School
import kotlinx.coroutines.launch

class SchoolManagementViewModel(application: Application) : AndroidViewModel(application) {

    private val schoolDao = AppDatabase.getDatabase(application).schoolDao()

    val allSchools: LiveData<List<School>> = schoolDao.getAllSchools().asLiveData()

    fun deleteSchool(school: School) {
        viewModelScope.launch {
            schoolDao.delete(school)
        }
    }
}
