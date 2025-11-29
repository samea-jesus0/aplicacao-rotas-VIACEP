package com.example.rotasescolares.ui.tripcontrol

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.rotasescolares.data.Student
import com.example.rotasescolares.data.StudentDao
import kotlinx.coroutines.launch
import androidx.lifecycle.switchMap

class TripControlViewModel(private val studentDao: StudentDao) : ViewModel() {

    private val _shift = MutableLiveData<String>("Manhã")

    val students: LiveData<List<Student>> = _shift.switchMap {
        studentDao.getStudentsByShift(it).asLiveData()
    }

    fun setShift(shift: String) {
        _shift.value = shift
    }

    fun onStudentClicked(student: Student) {
        viewModelScope.launch {
            val newStatus = when (student.status) {
                StudentTripStatus.WAITING -> StudentTripStatus.ON_BOARD
                StudentTripStatus.ON_BOARD -> StudentTripStatus.DROPPED_OFF
                else -> student.status
            }
            studentDao.update(student.copy(status = newStatus))
        }
    }

    fun resetTrip() {
        _shift.value?.let {
            viewModelScope.launch {
                studentDao.resetStatusByShift(it, StudentTripStatus.WAITING)
            }
        }
    }
}
