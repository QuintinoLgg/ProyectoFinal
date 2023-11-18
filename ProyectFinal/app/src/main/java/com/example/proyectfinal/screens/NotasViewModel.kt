package com.example.proyectfinal.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyectfinal.data.Nota
import com.example.proyectfinal.data.NotasRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class NotasViewModel(private val notasRepository: NotasRepository): ViewModel(){

    val notasUiState: StateFlow<NotasUiState> =
        notasRepository.getAllNotasStream().map { NotasUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = NotasUiState()
            )

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}

data class NotasUiState(val itemList: List<Nota> = listOf())