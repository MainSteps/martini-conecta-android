package com.mainsteps.martiniconecta.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mainsteps.martiniconecta.model.Dificultad
import com.mainsteps.martiniconecta.model.Division
import com.mainsteps.martiniconecta.model.Ingrediente

@Entity(tableName = "favoritos")
data class FavoritoEntity(
    @PrimaryKey val id: String,
    val titulo: String,
    val descripcion: String,
    val division: Division,
    val categoria: String,
    val dificultad: Dificultad,
    val productosMartini: List<String>,
    val rindePorciones: Int,
    val ingredientes: List<Ingrediente>,
    val pasos: List<String>,
    val imagenResId: Int,
    val esVegano: Boolean,
)
