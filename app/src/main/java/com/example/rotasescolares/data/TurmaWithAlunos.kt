package com.example.rotasescolares.data

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class TurmaWithAlunos(
    @Embedded val turma: Turma,
    @Relation(
        parentColumn = "turmaId",
        entity = Student::class,
        entityColumn = "id",
        associateBy = Junction(
            value = TurmaAlunoCrossRef::class,
            parentColumn = "turmaId",
            entityColumn = "alunoId"
        )
    )
    val alunos: List<Student>
)
