package com.example.proyectfinal.data

import kotlinx.coroutines.flow.Flow

class OfflineNotasRepository (private val notaDao: NotaDao) : NotasRepository {
    override fun getAllNotasStream(): Flow<List<Nota>> = notaDao.getAllNotas()
    override fun getNotaStream(id: Int): Flow<Nota?> = notaDao.getNota(id)
    override suspend fun insertNota(nota: Nota) = notaDao.insert(nota)
    override suspend fun deleteNota(nota: Nota) = notaDao.delete(nota)
    override suspend fun updateNota(nota: Nota) = notaDao.update(nota)

}