package com.example.rotasescolares.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface TurmaDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTurma(turma: Turma): Long
    @Update
    suspend fun updateTurma(turma: Turma)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTurmaAlunoCrossRef(crossRef: TurmaAlunoCrossRef)
    @Query("DELETE FROM turma_aluno_cross_ref WHERE turmaId = :turmaId")
    suspend fun deleteTurmaAlunoCrossRefsByTurmaId(turmaId: Long)
    @Delete
    suspend fun delete(turma: Turma)
    @Transaction
    @Query("SELECT * FROM turmas")
    fun getTurmasWithAlunos(): Flow<List<TurmaWithAlunos>>
    @Transaction
    @Query("SELECT * FROM turmas WHERE turmaId = :classId")
    fun getTurmaWithAlunosById(classId: Long): Flow<TurmaWithAlunos?>
    @Query("DELETE FROM turmas WHERE turmaId = :turmaId")
    suspend fun deleteTurma(turmaId: Long)
}
