package com.example.rotasescolares.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "turmas",
    foreignKeys = [
        ForeignKey(
            entity = School::class,
            parentColumns = ["schoolId"],
            childColumns = ["schoolOwnerId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["schoolOwnerId"])]
)
data class Turma(
    @PrimaryKey(autoGenerate = true)
    val turmaId: Long = 0,
    val name: String,
    val shift: String,
    val schoolOwnerId: Long
)
