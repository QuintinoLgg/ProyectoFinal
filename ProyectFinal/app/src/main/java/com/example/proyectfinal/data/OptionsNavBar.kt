package com.example.proyectfinal.data

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Task
import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavItem(
    val nombre: String,
    val ruta: String,
    val icono: ImageVector
)

val bottomNavItems = listOf(
    BottomNavItem(
        nombre = "Notas",
        ruta = "notes_screen",
        icono = Icons.Filled.Description,
    ),
    BottomNavItem(
        nombre = "Tareas",
        ruta = "tasks_screen",
        icono = Icons.Filled.Task,
    ),
    BottomNavItem(
        nombre = "Agregar",
        ruta = "add_note_screen",
        icono = Icons.Filled.Add,
    )
)