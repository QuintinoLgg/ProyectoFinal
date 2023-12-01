package com.example.proyectfinal.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.proyectfinal.Constants

@Entity(tableName = Constants.TasksTable.NAME)
data class Task(
    @PrimaryKey(autoGenerate = true) val id: Int?= null,
    @ColumnInfo(name = "titulo") val titulo: String,
    @ColumnInfo(name = "descripcion") val descripcion: String,
    @ColumnInfo(name = "fecha") val fecha: String
)

@Entity(tableName = Constants.NotesTable.NAME)
data class Note(
    @PrimaryKey(autoGenerate = true) var id: Int? = null,
    @ColumnInfo(name = "titulo") val titulo: String,
    @ColumnInfo(name = "descripcion") val descripcion: String
)