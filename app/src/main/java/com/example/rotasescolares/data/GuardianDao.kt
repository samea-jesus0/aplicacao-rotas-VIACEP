package com.example.rotasescolares.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface GuardianDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(guardian: Guardian)

    @Update
    suspend fun update(guardian: Guardian)

    @Delete
    suspend fun delete(guardian: Guardian)

    @Query("SELECT * FROM Guardian WHERE guardianId = :guardianId")
    fun getGuardianById(guardianId: Long): Flow<Guardian?>

    @Query("SELECT * FROM Guardian ORDER BY name ASC")
    fun getAllGuardians(): Flow<List<Guardian>>
}