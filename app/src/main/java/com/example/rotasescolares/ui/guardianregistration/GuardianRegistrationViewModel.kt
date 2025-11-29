package com.example.rotasescolares.ui.guardianregistration

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.rotasescolares.data.AppDatabase
import com.example.rotasescolares.data.Guardian
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class GuardianRegistrationViewModel(
    application: Application,
    private val guardianId: Long
) : AndroidViewModel(application) {

    private val guardianDao = AppDatabase.getDatabase(application).guardianDao()

    private val _guardian = MutableLiveData<Guardian?>()
    val guardian: LiveData<Guardian?> = _guardian

    private val _registrationResult = MutableLiveData<Boolean>()
    val registrationResult: LiveData<Boolean> = _registrationResult

    init {
        if (guardianId != -1L) {
            viewModelScope.launch {
                _guardian.value = guardianDao.getGuardianById(guardianId).first()
            }
        }
    }

    fun saveGuardian(
        name: String,
        cpf: String,
        phone: String,
        secondaryPhone: String?,
        email: String?,
        relationship: String
    ) {
        viewModelScope.launch {
            val guardianToSave = _guardian.value?.copy(
                name = name,
                cpf = cpf,
                phone = phone,
                secondaryPhone = secondaryPhone,
                email = email,
                relationship = relationship
            ) ?: Guardian(
                name = name,
                cpf = cpf,
                phone = phone,
                secondaryPhone = secondaryPhone,
                email = email,
                relationship = relationship
            )

            if (guardianId == -1L) {
                guardianDao.insert(guardianToSave)
            } else {
                guardianDao.update(guardianToSave)
            }
            _registrationResult.postValue(true)
        }
    }
}
