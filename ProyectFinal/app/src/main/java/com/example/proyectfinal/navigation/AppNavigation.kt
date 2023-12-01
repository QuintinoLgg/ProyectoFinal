package com.example.proyectfinal.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.proyectfinal.screens.AddNoteScreen
import com.example.proyectfinal.screens.AddTaskScreen
import com.example.proyectfinal.screens.EditNoteScreen
import com.example.proyectfinal.screens.EditTaskScreen
import com.example.proyectfinal.screens.NotesScreen
import com.example.proyectfinal.screens.TasksScreen
import com.example.proyectfinal.ui.miViewModel
import com.example.proyectfinal.ui.utils.NotesAppNavigationType

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AppNavigation(
    viewModel: miViewModel,
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
            NotesScreen(viewModel, navController, navigationType)
        }
        composable(route = AppScreens.TasksScreen.route){
            TasksScreen(viewModel, navController, navigationType)
        }
        composable(route = AppScreens.AddNoteScreen.route){
            AddNoteScreen(viewModel, navController, navigationType)
        }
        composable(route = AppScreens.AddTaskScreen.route){
            AddTaskScreen(viewModel, navController, navigationType)
        }
        composable(route = AppScreens.EditNoteScreen.route){
            EditNoteScreen(viewModel, navController, navigationType)
        }
        composable(route = AppScreens.EditTaskScreen.route){
            EditTaskScreen(viewModel, navController, navigationType)
        }
    }
}