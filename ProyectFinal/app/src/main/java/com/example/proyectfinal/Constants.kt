package com.example.proyectfinal

import com.example.proyectfinal.models.Note
import com.example.proyectfinal.models.Task

class Constants {

    object General {
        const val NAME = "AppDatabase"
        var nota = Note(0,"","", emptyList(), emptyList())
        var tarea = Task(0, "", "", "", emptyList(), emptyList())
    }


    object NotesTable {
        const val NAME = "notes"
        const val TITLE = "title"
        const val DESC = "desc"
        const val IMAGE = "images"
        const val VIDEO = "video"
    }

    object TasksTable {
        const val NAME = "tasks"
        const val TITLE = "title"
        const val DESC = "desc"
        const val DATE = "date"
        const val IMAGE = "images"
        const val VIDEO = "video"
    }

}