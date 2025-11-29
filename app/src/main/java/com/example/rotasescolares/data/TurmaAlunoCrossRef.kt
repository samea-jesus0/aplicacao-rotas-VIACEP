package com.example.rotasescolares.data

import androidx.room.Entity
import androidx.room.Index

@Entity(
    tableName = "turma_aluno_cross_ref",
    primaryKeys = ["turmaId", "alunoId"],
    indices = [Index(value = ["alunoId"])]
)
data class TurmaAlunoCrossRef(
    val turmaId: Long,
    val alunoId: Long
)
