package com.mainsteps.martiniconecta.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface PerfilDao {

    @Query("SELECT * FROM perfil WHERE id = 1")
    fun obtenerPerfil(): Flow<PerfilEntity?>

    @Query("SELECT * FROM perfil WHERE id = 1")
    suspend fun obtenerPerfilActual(): PerfilEntity?

    @Upsert
    suspend fun guardarPerfil(perfil: PerfilEntity)
}
