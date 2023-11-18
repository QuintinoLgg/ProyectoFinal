package com.example.proyectfinal.data
import kotlinx.coroutines.flow.Flow

interface NotasRepository {
    fun getAllNotasStream(): Flow<List<Nota>>
    fun getNotaStream(id: Int): Flow<Nota?>
    suspend fun insertNota(nota: Nota)
    suspend fun deleteNota(nota: Nota)
    suspend fun updateNota(nota: Nota)
}