package com.example.rotasescolares.ui.studentregistration

import android.app.Application
import androidx.lifecycle.*
import com.example.rotasescolares.ViaCepResponse
import com.example.rotasescolares.data.*
import com.example.rotasescolares.network.ApiClient
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StudentRegistrationViewModel(
    application: Application,
    private val studentId: Long
) : AndroidViewModel(application) {

    private val guardianDao = AppDatabase.getDatabase(application).guardianDao()
    private val schoolDao = AppDatabase.getDatabase(application).schoolDao()
    private val studentDao = AppDatabase.getDatabase(application).studentDao()

    private val _student = MutableLiveData<Student?>()
    val student: LiveData<Student?> = _student

    private val _viaCepResponse = MutableLiveData<ViaCepResponse?>()
    val viaCepResponse: LiveData<ViaCepResponse?> = _viaCepResponse

    private val _cepError = MutableLiveData<String>()
    val cepError: LiveData<String> = _cepError

    val allGuardians: LiveData<List<Guardian>> = guardianDao.getAllGuardians().asLiveData()
    val allSchools: LiveData<List<School>> = schoolDao.getAllSchools().asLiveData()

    private val _studentSaved = MutableLiveData<Boolean>()
    val studentSaved: LiveData<Boolean> = _studentSaved

    init {
        if (studentId != -1L) {
            viewModelScope.launch {
                _student.value = studentDao.getStudentById(studentId).first()
            }
        }
    }

    fun buscarCep(cep: String) {
        ApiClient.service.buscarCep(cep).enqueue(object : Callback<ViaCepResponse> {
            override fun onResponse(call: Call<ViaCepResponse>, response: Response<ViaCepResponse>) {
                if (response.isSuccessful) {
                    val viaCepResponse = response.body()
                    if (viaCepResponse != null && !viaCepResponse.erro) {
                        _viaCepResponse.postValue(viaCepResponse)
                    } else {
                        _cepError.postValue("CEP inválido")
                        _viaCepResponse.postValue(null)
                    }
                } else {
                    _cepError.postValue("Erro ao buscar CEP")
                    _viaCepResponse.postValue(null)
                }
            }

            override fun onFailure(call: Call<ViaCepResponse>, t: Throwable) {
                _cepError.postValue("Falha na rede: ${t.message}")
                _viaCepResponse.postValue(null)
            }
        })
    }

    fun saveStudent(
        fullName: String,
        dateOfBirth: String,
        cep: String,
        logradouro: String,
        numero: String,
        complemento: String?,
        bairro: String,
        cidade: String,
        uf: String,
        guardianName: String,
        schoolName: String,
        shift: String,
        notes: String?
    ) {
        viewModelScope.launch {
            val guardianId = allGuardians.value?.find { it.name == guardianName }?.guardianId
            val schoolId = allSchools.value?.find { it.name == schoolName }?.schoolId

            if (guardianId == null || schoolId == null) {
                // Handle error
                return@launch
            }

            val studentToSave = student.value?.copy(
                fullName = fullName,
                dateOfBirth = dateOfBirth,
                cep = cep,
                logradouro = logradouro,
                numero = numero,
                complemento = complemento,
                bairro = bairro,
                cidade = cidade,
                uf = uf,
                guardianId = guardianId,
                schoolId = schoolId,
                shift = shift,
                notes = notes
            ) ?: Student(
                fullName = fullName,
                dateOfBirth = dateOfBirth,
                cep = cep,
                logradouro = logradouro,
                numero = numero,
                complemento = complemento,
                bairro = bairro,
                cidade = cidade,
                uf = uf,
                guardianId = guardianId,
                schoolId = schoolId,
                shift = shift,
                notes = notes
            )

            if (studentId == -1L) {
                studentDao.insert(studentToSave)
            } else {
                studentDao.update(studentToSave)
            }
            _studentSaved.postValue(true)
        }
    }

    fun onStudentSaved() {
        _studentSaved.value = false
    }
}
