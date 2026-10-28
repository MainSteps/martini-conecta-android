package com.mainsteps.martiniconecta.repository

import com.mainsteps.martiniconecta.data.local.FavoritoDao
import com.mainsteps.martiniconecta.data.local.FavoritoEntity
import com.mainsteps.martiniconecta.model.Contenido
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface FavoritosDataSource {
    fun observarFavoritos(): Flow<List<Contenido>>
    suspend fun alternarFavorito(contenido: Contenido)
}

class FavoritosRepository(
    private val favoritoDao: FavoritoDao,
) : FavoritosDataSource {

    override fun observarFavoritos(): Flow<List<Contenido>> =
        favoritoDao.observarFavoritos().map { favoritos ->
            favoritos.map(FavoritoEntity::toContenido)
        }

    override suspend fun alternarFavorito(contenido: Contenido) {
        val favorito = contenido.toEntity()
        if (favoritoDao.existe(contenido.id)) {
            favoritoDao.quitar(favorito)
        } else {
            favoritoDao.guardar(favorito)
        }
    }
}

private fun Contenido.toEntity() = FavoritoEntity(
    id = id,
    titulo = titulo,
    descripcion = descripcion,
    division = division,
    categoria = categoria,
    dificultad = dificultad,
    productosMartini = productosMartini,
    rindePorciones = rindePorciones,
    ingredientes = ingredientes,
    pasos = pasos,
    imagenResId = imagenResId,
    esVegano = esVegano,
)

private fun FavoritoEntity.toContenido() = Contenido(
    id = id,
    titulo = titulo,
    descripcion = descripcion,
    division = division,
    categoria = categoria,
    dificultad = dificultad,
    productosMartini = productosMartini,
    rindePorciones = rindePorciones,
    ingredientes = ingredientes,
    pasos = pasos,
    imagenResId = imagenResId,
    esVegano = esVegano,
)
