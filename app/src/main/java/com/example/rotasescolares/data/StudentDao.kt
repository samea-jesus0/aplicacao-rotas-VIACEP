package com.example.rotasescolares.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.rotasescolares.ui.tripcontrol.StudentTripStatus
import kotlinx.coroutines.flow.Flow

@Dao
interface StudentDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(student: Student)

    @Update
    suspend fun update(student: Student)

    @Delete
    suspend fun delete(student: Student)

    @Query("SELECT * FROM students WHERE id = :studentId")
    fun getStudentById(studentId: Long): Flow<Student?>

    @Query("SELECT * FROM students WHERE fullName LIKE '%' || :searchQuery || '%' ORDER BY fullName ASC")
    fun getAllStudents(searchQuery: String): Flow<List<Student>>

    @Query("SELECT * FROM students WHERE shift = :shift ORDER BY fullName ASC")
    fun getStudentsByShift(shift: String): Flow<List<Student>>

    @Query("UPDATE students SET status = :status WHERE shift = :shift")
    suspend fun resetStatusByShift(shift: String, status: StudentTripStatus)
}
