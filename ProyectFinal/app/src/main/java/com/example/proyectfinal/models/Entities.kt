package com.example.proyectfinal.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.proyectfinal.Constants

@Entity(tableName = Constants.TasksTable.NAME)
data class Task(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = Constants.TasksTable.TITLE) val titulo: String,
    @ColumnInfo(name = Constants.TasksTable.DESC) val descripcion: String,
    @ColumnInfo(name = Constants.TasksTable.DATE) val fecha: String,
    @ColumnInfo(name = Constants.TasksTable.IMAGE) val images: String? = null
)

@Entity(tableName = Constants.NotesTable.NAME)
data class Note(
    @PrimaryKey(autoGenerate = true) var id: Int,
    @ColumnInfo(name = Constants.NotesTable.TITLE) val titulo: String,
    @ColumnInfo(name = Constants.NotesTable.DESC) val descripcion: String,
    @ColumnInfo(name = Constants.NotesTable.IMAGE) val images: String? = null,
)