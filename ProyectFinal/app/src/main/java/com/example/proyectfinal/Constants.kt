package com.example.proyectfinal

import com.example.proyectfinal.models.Note
import com.example.proyectfinal.models.Task

class Constants {

    object General {
        const val NAME = "AppDatabase"
        var nota = Note(0,"","")
        var tarea = Task(0, "", "", "")
    }


    object NotesTable {
        const val NAME = "notes"
        const val TITLE = "title"
        const val DESC = "desc"
    }

    object TasksTable {
        const val NAME = "tasks"
        const val TITLE = "title"
        const val DESC = "desc"
        const val DATE = "date"
    }

}