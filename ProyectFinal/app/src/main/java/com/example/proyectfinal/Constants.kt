package com.example.proyectfinal

import androidx.compose.runtime.mutableStateOf
import com.example.proyectfinal.models.Note

object Constants {
    const val TABLE_NAME = "Notes"
    const val DATABASE_NAME = "NotesDatabase"
    var NOTA_EDITAR = 0

    fun List<Note>?.orPlaceHolderList(): List<Note>{
        fun placeHolderList(): List<Note> {
            return listOf(Note(titulo = "Ni una nota encontrada", descripcion = "Empieza creando alguna nota"))
        }
        return if (this!=null && this.isNotEmpty()){
            this
        } else placeHolderList()
    }
}