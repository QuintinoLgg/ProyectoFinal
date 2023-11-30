package com.example.proyectfinal.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.proyectfinal.models.Note

@Database(entities = [Note::class], version = 1)
abstract class NotesDatabase : RoomDatabase() {
    abstract fun NotesDao(): NotesDao
}