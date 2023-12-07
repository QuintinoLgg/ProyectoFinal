package com.example.proyectfinal.ui

import android.content.Context
import androidx.lifecycle.*
import androidx.room.Database
import androidx.room.Room
import com.example.proyectfinal.Constants
import com.example.proyectfinal.UiState
import com.example.proyectfinal.data.AppDatabase
import com.example.proyectfinal.models.Note
import com.example.proyectfinal.models.Task
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class miViewModel(
    private val context: Context,
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()
    private val db = Room.databaseBuilder(context, AppDatabase::class.java, Constants.General.NAME).build()



    init {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.value = UiState(
                notes = db.NotesDao().getNotes(),
                tasks = db.TaskDao().getTasks()
            )
        }
    }

    fun renderizado(){
        _uiState.update {
            it.copy(
                notes = db.NotesDao().getNotes(),
                tasks = db.TaskDao().getTasks()
            )
        }
    }

    fun insertNote(note: Note){
        viewModelScope.launch(Dispatchers.IO){
            db.NotesDao().insertNote(note)
            // si no renderiza, actualizar la lista
            renderizado()
        }
    }

    fun updateNote(note: Note){
        viewModelScope.launch(Dispatchers.IO){
            db.NotesDao().updateNote(note)
            renderizado()
        }
    }

    fun deleteNote(note: Note){
        viewModelScope.launch(Dispatchers.IO){
            db.NotesDao().deleteNote(note)
            renderizado()
        }
    }

    fun getAllNotes(){
        viewModelScope.launch(Dispatchers.IO){
            db.NotesDao().getNotes()
            renderizado()
        }
    }

    fun insertTask(task: Task){
        viewModelScope.launch(Dispatchers.IO){
            db.TaskDao().insertTask(task)
            renderizado()
        }
    }

    fun updateTask(task: Task){
        viewModelScope.launch(Dispatchers.IO){
            db.TaskDao().updateTask(task)
            renderizado()
        }
    }

    fun deleteTask(task: Task){
        viewModelScope.launch(Dispatchers.IO){
            db.TaskDao().deleteTask(task)
            renderizado()
        }
    }

    fun getAllTasks(){
        viewModelScope.launch(Dispatchers.IO){
            db.TaskDao().getTasks()
            renderizado()
        }
    }
}

