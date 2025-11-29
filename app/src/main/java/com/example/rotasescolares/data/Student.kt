package com.example.rotasescolares.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.rotasescolares.ui.tripcontrol.StudentTripStatus

@Entity(tableName = "students",
    foreignKeys = [
    ForeignKey(entity = Guardian::class,
        parentColumns = ["guardianId"],
        childColumns = ["guardianId"]),
    ForeignKey(entity = School::class,
        parentColumns = ["schoolId"],
        childColumns = ["schoolId"])
    ],
    indices = [Index(value = ["guardianId"]), Index(value = ["schoolId"])]
)
@TypeConverters(Converters::class)
data class Student(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val fullName: String,
    val dateOfBirth: String,
    val cep: String,
    val logradouro: String,
    val bairro: String,
    val cidade: String,
    val uf: String,
    val photoPath: String? = null,
    val notes: String? = null,
    val guardianId: Long,
    val schoolId: Long,
    val shift: String, // "Morning", "Afternoon", "Night"
    val status: StudentTripStatus = StudentTripStatus.WAITING
)
