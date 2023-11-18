package com.example.proyectfinal.data
import androidx.room.Database
import androidx.room.RoomDatabase
import android.content.Context
import androidx.room.Room

@Database(entities = [Nota::class], version = 1, exportSchema = false)
abstract class NotaDatabase : RoomDatabase(){

    abstract fun notaDao(): NotaDao

    companion object {
        @Volatile
        private var Instance: NotaDatabase? = null

        fun getDatabase(context: Context): RoomDatabase{
            return Instance ?: synchronized(this){
                Room.databaseBuilder(context, NotaDatabase::class.java, "nota_database")
                    .build()
                    .also { Instance = it }
            }
        }
    }
}
