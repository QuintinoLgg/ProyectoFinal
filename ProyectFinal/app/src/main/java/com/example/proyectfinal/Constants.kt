package com.example.proyectfinal

import com.example.proyectfinal.models.Note

object Constants {
    const val TABLE_NAME = "Notes"
    const val DATABASE_NAME = "NotesDatabase"

    fun List<Note>?.orPlaceHolderList(): List<Note>{
        fun placeHolderList(): List<Note> {
            return listOf(Note(titulo = "Ni una nota encontrada", descripcion = "Empieza creando alguna nota"))
        }
        return if (this!=null && this.isNotEmpty()){
            this
        } else placeHolderList()
    }
}