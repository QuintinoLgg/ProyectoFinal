package com.example.proyectfinal.navigation

sealed class AppScreens (val route: String){
    object NotesScreen: AppScreens("notes_screen")
    object TasksScreen: AppScreens("tasks_screen")
    object AddNoteScreen: AppScreens("add_note_screen")
    object AddTaskScreen: AppScreens("add_task_screen")
    object EditNoteScreen: AppScreens("edit_note_screen")
    object EditTaskScreen: AppScreens("edit_task_screen")


}
