package com.example.rotasescolares.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "funcionarios")
data class Funcionario(
    @PrimaryKey(autoGenerate = true)
    val funcionarioId: Long = 0,
    val nome: String,
    val cpf: String,
    val telefone: String,
    val email: String?,
    val cargo: String,
    val turno: String // "Morning", "Afternoon", "Night"
)
