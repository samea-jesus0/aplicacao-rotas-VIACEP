package com.example.rotasescolares.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface SchoolDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(school: School)

    @Update
    suspend fun update(school: School)

    @Delete
    suspend fun delete(school: School)

    @Query("SELECT * FROM schools WHERE schoolId = :schoolId")
    fun getSchoolById(schoolId: Long): Flow<School?>

    @Query("SELECT * FROM schools ORDER BY name ASC")
    fun getAllSchools(): Flow<List<School>>
}
