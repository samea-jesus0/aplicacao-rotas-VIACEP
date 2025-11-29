package com.example.rotasescolares.ui.studentmanagement

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.rotasescolares.data.AppDatabase
import com.example.rotasescolares.data.Student
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
class StudentManagementViewModel(application: Application) : AndroidViewModel(application) {

    private val studentDao = AppDatabase.getDatabase(application).studentDao()
    private val searchQuery = MutableStateFlow("")

    val allStudents = searchQuery.flatMapLatest {
        studentDao.getAllStudents(it)
    }.asLiveData()

    fun setSearchQuery(query: String) {
        searchQuery.value = query
    }

    fun deleteStudent(student: Student) {
        viewModelScope.launch {
            studentDao.delete(student)
        }
    }
}