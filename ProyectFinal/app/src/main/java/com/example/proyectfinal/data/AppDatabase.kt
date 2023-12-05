package com.example.proyectfinal.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.proyectfinal.models.Converters
import com.example.proyectfinal.models.Note
import com.example.proyectfinal.models.Task


@Database(entities = [Note::class, Task::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun NotesDao(): NotesDao
    abstract fun TaskDao(): TaskDao
}