package com.example.proyectfinal

import com.example.proyectfinal.models.Note
import com.example.proyectfinal.models.Task


data class UiState(
    val notes: List<Note> = listOf(),
    val tasks: List<Task> = listOf()
)