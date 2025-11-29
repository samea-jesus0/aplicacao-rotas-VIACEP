package com.example.rotasescolares.ui.classregistration

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.rotasescolares.data.AppDatabase
import com.example.rotasescolares.data.School
import com.example.rotasescolares.data.Student
import com.example.rotasescolares.data.Turma
import com.example.rotasescolares.data.TurmaAlunoCrossRef
import com.example.rotasescolares.data.TurmaWithAlunos
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class ClassRegistrationViewModel(
    application: Application,
    private val classId: Long
) : AndroidViewModel(application) {

    private val schoolDao = AppDatabase.getDatabase(application).schoolDao()
    private val studentDao = AppDatabase.getDatabase(application).studentDao()
    private val turmaDao = AppDatabase.getDatabase(application).turmaDao()

    private val _turma = MutableLiveData<TurmaWithAlunos?>()
    val turma: LiveData<TurmaWithAlunos?> = _turma

    val allSchools: LiveData<List<School>> = schoolDao.getAllSchools().asLiveData()
    val allStudents: LiveData<List<Student>> = studentDao.getAllStudents("").asLiveData()

    private val _classSaved = MutableLiveData<Boolean>()
    val classSaved: LiveData<Boolean> = _classSaved

    init {
        if (classId != -1L) {
            viewModelScope.launch {
                _turma.value = turmaDao.getTurmaWithAlunosById(classId).first()
            }
        }
    }

    fun saveClass(
        className: String,
        shift: String,
        schoolName: String,
        selectedStudentIds: List<Long>
    ) {
        viewModelScope.launch {
            val schoolId = allSchools.value?.find { it.name == schoolName }?.schoolId
            if (schoolId == null) {
                // Handle error
                return@launch
            }

            val turmaToSave = if (classId == -1L) {
                // New class
                Turma(name = className, shift = shift, schoolOwnerId = schoolId)
            } else {
                // Existing class
                Turma(turmaId = classId, name = className, shift = shift, schoolOwnerId = schoolId)
            }

            if (classId == -1L) {
                val newTurmaId = turmaDao.insertTurma(turmaToSave)
                selectedStudentIds.forEach { studentId ->
                    turmaDao.insertTurmaAlunoCrossRef(TurmaAlunoCrossRef(newTurmaId, studentId))
                }
            } else {
                turmaDao.updateTurma(turmaToSave)
                // TODO: Handle student list updates correctly (e.g., clear old and add new)
                turmaDao.deleteTurmaAlunoCrossRefsByTurmaId(classId)
                selectedStudentIds.forEach { studentId ->
                    turmaDao.insertTurmaAlunoCrossRef(TurmaAlunoCrossRef(classId, studentId))
                }
            }

            _classSaved.postValue(true)
        }
    }
}
