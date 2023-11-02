package com.example.proyectfinal.navigation

sealed class AppScreens (val route: String){
    object NotesScreen: AppScreens("notes_screen")
    object TasksScreen: AppScreens("tasks_screen")
    object AddEditNoteScreen: AppScreens("add_edit_note_screen")

}
