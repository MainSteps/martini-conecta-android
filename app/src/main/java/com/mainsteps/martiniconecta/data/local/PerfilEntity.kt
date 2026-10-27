package com.mainsteps.martiniconecta.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mainsteps.martiniconecta.model.Division
import com.mainsteps.martiniconecta.model.Interes
import com.mainsteps.martiniconecta.model.TamanoNegocio

@Entity(tableName = "perfil")
data class PerfilEntity(
    @PrimaryKey val id: Int = PERFIL_ID,
    val nombre: String,
    val email: String,
    val telefono: String?,
    val division: Division,
    val tamano: TamanoNegocio,
    val intereses: Set<Interes>,
    val fotoUri: String?,
    val letraGrande: Boolean = false,
) {
    companion object {
        const val PERFIL_ID = 1
    }
}
