package com.example.rotasescolares.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface FuncionarioDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(funcionario: Funcionario)

    @Update
    suspend fun update(funcionario: Funcionario)

    @Delete
    suspend fun delete(funcionario: Funcionario)

    @Query("SELECT * FROM funcionarios WHERE funcionarioId = :funcionarioId")
    fun getFuncionarioById(funcionarioId: Long): Flow<Funcionario?>

    @Query("SELECT * FROM funcionarios ORDER BY nome ASC")
    fun getAllFuncionarios(): Flow<List<Funcionario>>
}
