package com.example.rotasescolares.data

import androidx.room.TypeConverter
import com.example.rotasescolares.ui.tripcontrol.StudentTripStatus

class Converters {
    @TypeConverter
    fun fromStatus(status: StudentTripStatus): String {
        return status.name
    }

    @TypeConverter
    fun toStatus(status: String): StudentTripStatus {
        return StudentTripStatus.valueOf(status)
    }
}