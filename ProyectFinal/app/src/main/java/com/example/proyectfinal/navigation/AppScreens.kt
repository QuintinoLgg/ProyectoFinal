package com.example.proyectfinal.navigation

sealed class AppScreens (val route: String){
    object MainScreen: AppScreens("main_screen")
    object AddEditScreen: AppScreens("add_edit_screen")
}
