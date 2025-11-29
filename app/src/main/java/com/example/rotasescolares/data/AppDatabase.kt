package com.example.rotasescolares.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [Usuario::class, School::class, Turma::class, TurmaAlunoCrossRef::class, Guardian::class, Student::class, Funcionario::class], version = 8, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun usuarioDao(): UsuarioDao
    abstract fun schoolDao(): SchoolDao
    abstract fun turmaDao(): TurmaDao
    abstract fun guardianDao(): GuardianDao
    abstract fun studentDao(): StudentDao
    abstract fun funcionarioDao(): FuncionarioDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "rotas_escolares_db"
                )
                .fallbackToDestructiveMigration()
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
