package com.example.proyectfinal.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.proyectfinal.screens.AddEditNoteScreen
import com.example.proyectfinal.screens.NotesScreen
import com.example.proyectfinal.screens.TasksScreen
import com.example.proyectfinal.ui.utils.NotesAppNavigationType

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AppNavigation(
    windowSize: WindowWidthSizeClass
){
    val navigationType: NotesAppNavigationType
    val navController = rememberNavController()

    when(windowSize){
        WindowWidthSizeClass.Compact -> {
            navigationType = NotesAppNavigationType.BOTTOM_NAVIGATION
        }
        WindowWidthSizeClass.Medium -> {
            navigationType = NotesAppNavigationType.NAVIGATION_RAIL
        }
        WindowWidthSizeClass.Expanded -> {
            navigationType = NotesAppNavigationType.PERMANENT_NAVIGATION_DRAWER
        }
        else -> {
            navigationType = NotesAppNavigationType.BOTTOM_NAVIGATION
        }
    }

    NavHost(
        navController = navController,
        startDestination = AppScreens.NotesScreen.route
    ){
        composable(route = AppScreens.NotesScreen.route){
            NotesScreen(navController, navigationType)
        }
        composable(route = AppScreens.TasksScreen.route){
            TasksScreen(navController, navigationType)
        }
        composable(route = AppScreens.AddEditNoteScreen.route){
            AddEditNoteScreen(navController, navigationType)
        }
    }
}