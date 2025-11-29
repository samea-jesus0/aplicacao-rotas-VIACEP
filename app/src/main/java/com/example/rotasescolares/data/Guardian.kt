package com.example.rotasescolares.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Guardian(
    @PrimaryKey(autoGenerate = true)
    val guardianId: Long = 0,
    val name: String,
    val cpf: String,
    val phone: String,
    val secondaryPhone: String?,
    val email: String?,
    val relationship: String
)