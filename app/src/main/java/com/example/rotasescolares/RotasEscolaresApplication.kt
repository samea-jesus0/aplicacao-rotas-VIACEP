package com.example.rotasescolares

import android.app.Application
import com.example.rotasescolares.data.AppDatabase

class RotasEscolaresApplication : Application() {
    val database: AppDatabase by lazy { AppDatabase.getDatabase(this) }
}
