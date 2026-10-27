package com.mainsteps.martiniconecta.model

data class Contenido(
    val id: String,
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
    val esVegano: Boolean
)
