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


    var subject: MutableState<String> = mutableStateOf("Frecuencia")
    var milisegundos: MutableState<Long> = mutableStateOf<Long>(0)



    var description: MutableState<String> = mutableStateOf("")
    fun setDescription(newDescription: String) {
        description.value = newDescription
    }


    var destiny: MutableState<Double> = mutableStateOf(0.0)
    fun setdestiny(newdestiny: Double) {
        destiny.value = newdestiny
    }

    var destinyYears: MutableState<Long> = mutableStateOf(0)
    fun setdestinyYears(newdestinyYears: Long) {
        destinyYears.value = newdestinyYears
    }

    var destinyMonths: MutableState<Long> = mutableStateOf(0)
    fun setdestinyMonths(newdestinyMonths: Long) {
        destinyMonths.value = newdestinyMonths
    }

    var destinyDays: MutableState<Long> = mutableStateOf(0)
    fun setdestinyDays(newdestinyDays: Long) {
        destinyDays.value = newdestinyDays
    }

    var destinyHours: MutableState<Long> = mutableStateOf(0)
    fun setdestinyHours(newdestinyHours: Long) {
        destinyHours.value = newdestinyHours
    }

    var destinyMinutes: MutableState<Long> = mutableStateOf(0)
    fun setdestinyMinutes(newdestinyMinutes: Long) {
        destinyMinutes.value = newdestinyMinutes
    }

    var destinyFrecuencia: MutableState<Int> = mutableStateOf(0)
    fun setdestinyFrecuencia(newdestinyFrecuencia: Int) {
        destinyFrecuencia.value = newdestinyFrecuencia
    }


}

