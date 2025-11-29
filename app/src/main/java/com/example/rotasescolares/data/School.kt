package com.example.rotasescolares.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "schools")
data class School(
    @PrimaryKey(autoGenerate = true)
    val schoolId: Long = 0,
    val name: String,
    val address: String,
    val secretaryPhone: String,
    val contactName: String
)
