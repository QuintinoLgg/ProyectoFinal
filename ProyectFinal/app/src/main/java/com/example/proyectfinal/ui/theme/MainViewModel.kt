package com.example.proyectfinal.ui.theme

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class MainViewModel() : ViewModel() {
    var title: MutableState<String> = mutableStateOf("")
    fun setTitle(newTitle: String) {
        title.value = newTitle
    }


    var date: MutableState<String> = mutableStateOf("")
    fun setdate(newDate: String) {
        date.value = newDate
    }


    var subject: MutableState<String> = mutableStateOf("Tarea")



    var description: MutableState<String> = mutableStateOf("")
    fun setDescription(newDescription: String) {
        description.value = newDescription
    }


    var destiny: MutableState<Double> = mutableStateOf(0.0)
    fun setdestiny(newdestiny: Double) {
        destiny.value = newdestiny
    }

}

