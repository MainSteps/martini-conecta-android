package com.mainsteps.martiniconecta.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface SolicitudDao {

    @Insert
    suspend fun guardarSolicitud(solicitud: SolicitudEntity): Long

    @Query("SELECT * FROM solicitudes_contacto ORDER BY creadaEn DESC")
    fun observarSolicitudes(): Flow<List<SolicitudEntity>>
}
