package com.mainsteps.martiniconecta.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mainsteps.martiniconecta.model.Division
import com.mainsteps.martiniconecta.model.ProductoInteres

@Entity(tableName = "solicitudes_contacto")
data class SolicitudEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val nombre: String,
    val telefono: String,
    val division: Division,
    val comuna: String,
    val producto: ProductoInteres,
    val creadaEn: Long,
)
