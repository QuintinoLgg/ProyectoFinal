package com.example.proyectfinal.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.proyectfinal.models.Note
import com.example.proyectfinal.models.Task

@Dao
interface NotesDao {
    @Query("SELECT * FROM Notes WHERE Notes.id=:id")
    fun getNoteById(id: Int) : Note

    @Query("SELECT * FROM Notes")
    fun getNotes() : List<Note>

    @Delete
    fun deleteNote(note: Note)

    @Update
    fun updateNote(note: Note)

    @Insert
    fun insertNote(note: Note)
}


@Dao
interface TaskDao {
    @Query("SELECT * FROM Tasks WHERE Tasks.id=:id")
    fun getTaskById(id: Int) : Task

    @Query("SELECT * FROM Tasks")
    fun getTasks() : List<Task>

    @Delete
    fun deleteTask(task: Task)

    @Update
    fun updateTask(task: Task)

    @Insert
    fun insertTask(task: Task)
}