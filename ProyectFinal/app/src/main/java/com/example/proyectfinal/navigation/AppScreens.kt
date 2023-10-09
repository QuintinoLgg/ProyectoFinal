package com.example.proyectfinal.navigation

sealed class AppScreens (val route: String){
    object HomeScreen: AppScreens("home_screen")
    object AddEditNoteScreen: AppScreens("add_edit_note_screen")
}
