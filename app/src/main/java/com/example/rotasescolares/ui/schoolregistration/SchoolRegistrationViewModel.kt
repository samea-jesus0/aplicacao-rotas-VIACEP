package com.example.rotasescolares.ui.schoolregistration

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.rotasescolares.data.AppDatabase
import com.example.rotasescolares.data.School
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class SchoolRegistrationViewModel(
    application: Application,
    private val schoolId: Long
) : AndroidViewModel(application) {

    private val schoolDao = AppDatabase.getDatabase(application).schoolDao()

    private val _school = MutableLiveData<School?>()
    val school: LiveData<School?> = _school

    private val _schoolSaved = MutableLiveData<Boolean>()
    val schoolSaved: LiveData<Boolean> = _schoolSaved

    init {
        if (schoolId != -1L) {
            viewModelScope.launch {
                _school.value = schoolDao.getSchoolById(schoolId).first()
            }
        }
    }

    fun saveSchool(
        schoolName: String,
        address: String,
        secretaryPhone: String,
        contactName: String
    ) {
        viewModelScope.launch {
            val schoolToSave = _school.value?.copy(
                name = schoolName,
                address = address,
                secretaryPhone = secretaryPhone,
                contactName = contactName
            ) ?: School(
                name = schoolName,
                address = address,
                secretaryPhone = secretaryPhone,
                contactName = contactName
            )

            if (schoolId == -1L) {
                schoolDao.insert(schoolToSave)
            } else {
                schoolDao.update(schoolToSave)
            }
            _schoolSaved.postValue(true)
        }
    }

    fun onSchoolSaved() {
        _schoolSaved.value = false
    }
}