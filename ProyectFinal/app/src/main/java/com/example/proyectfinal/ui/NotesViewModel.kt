package com.example.proyectfinal.ui

import androidx.lifecycle.*
import com.example.proyectfinal.data.NotesDao
import com.example.proyectfinal.models.Note
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NotesViewModel (
    private val db: NotesDao,
) : ViewModel() {

    val notes: LiveData<List<Note>> = db.getNotes()

    fun deleteNotes(note: Note){
        viewModelScope.launch(Dispatchers.IO) {
            db.deleteNote(note)
        }
    }

    fun updateNote(note: Note){
        viewModelScope.launch(Dispatchers.IO){
            db.updateNote(note)
        }
    }

    fun createNote(titulo: String, descripcion: String){
        viewModelScope.launch(Dispatchers.IO){
            db.insertNote(Note(titulo = titulo, descripcion = descripcion))
        }
    }

    suspend fun getNote(noteId: Int) : Note? {
        return db.getNoteById(noteId)
    }
}


class NotesViewModelFactory(
    private val db: NotesDao,
): ViewModelProvider.NewInstanceFactory(){
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NotesViewModel(
            db = db
        ) as T
    }
}