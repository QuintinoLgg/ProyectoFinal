package com.example.proyectfinal.screens

import android.annotation.SuppressLint
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.proyectfinal.ui.miViewModel
import com.example.proyectfinal.ui.utils.NotesAppNavigationType

@Composable
fun BodyContentAddTask(notesViewModel: miViewModel, navController: NavController, navigationType: NotesAppNavigationType){

}


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AddTaskScreen(notesViewModel: miViewModel, navController: NavController, navigationType: NotesAppNavigationType){
    Scaffold {
        BodyContentAddTask(notesViewModel, navController, navigationType)
    }
}

