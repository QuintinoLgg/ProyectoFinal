package com.example.proyectfinal.data
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface NotaDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(nota: Nota)

    @Update
    suspend fun update(nota: Nota)

    @Delete
    suspend fun delete(nota: Nota)

    @Query("SELECT * FROM notas WHERE id = :id")
    fun getNota(id: Int): Flow<Nota>

    @Query("SELECT * FROM notas ORDER BY titulo ASC")
    fun getAllNotas(): Flow<List<Nota>>
}