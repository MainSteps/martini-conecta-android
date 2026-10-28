package com.mainsteps.martiniconecta.repository

import com.mainsteps.martiniconecta.data.local.FavoritoDao
import com.mainsteps.martiniconecta.data.local.FavoritoEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class FavoritosRepositoryTest {

    @Test
    fun `alternar guarda una vez y luego quita la receta`() = runBlocking {
        val dao = FavoritoDaoFalso()
        val repository = FavoritosRepository(dao)
        val receta = CatalogoRepository.obtenerTodos().first()

        repository.alternarFavorito(receta)
        repository.alternarFavorito(receta)

        assertTrue(repository.observarFavoritos().first().isEmpty())
    }

    @Test
    fun `favorito conserva el contenido completo`() = runBlocking {
        val dao = FavoritoDaoFalso()
        val repository = FavoritosRepository(dao)
        val receta = CatalogoRepository.obtenerTodos().first()

        repository.alternarFavorito(receta)

        assertEquals(receta, repository.observarFavoritos().first().single())
    }
}

private class FavoritoDaoFalso : FavoritoDao {
    private val favoritos = MutableStateFlow<List<FavoritoEntity>>(emptyList())

    override fun observarFavoritos(): Flow<List<FavoritoEntity>> = favoritos

    override suspend fun existe(id: String): Boolean =
        favoritos.value.any { it.id == id }

    override suspend fun guardar(favorito: FavoritoEntity) {
        favoritos.value = favoritos.value
            .filterNot { it.id == favorito.id } + favorito
    }

    override suspend fun quitar(favorito: FavoritoEntity) {
        favoritos.value = favoritos.value.filterNot { it.id == favorito.id }
    }
}
