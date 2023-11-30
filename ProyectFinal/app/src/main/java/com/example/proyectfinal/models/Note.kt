package com.example.proyectfinal.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.proyectfinal.Constants

@Entity(tableName = Constants.TABLE_NAME, indices = [Index(value = ["id"], unique = true)])
data class Note(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    @ColumnInfo(name = "titulo") val titulo: String,
    @ColumnInfo(name = "descripcion") val descripcion: String
)