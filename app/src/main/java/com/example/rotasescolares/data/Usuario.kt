package com.example.rotasescolares.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "usuarios")
data class Usuario(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nome: String,
    val cpf: String,
    val telefone: String,
    val email: String,
    val placaVan: String,
    val senhaCriptografada: String
)