package com.example.proyectfinal.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.proyectfinal.models.Note
import com.example.proyectfinal.models.Task


@Database(entities = [Note::class, Task::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun NotesDao(): NotesDao
    abstract fun TaskDao(): TaskDao
}