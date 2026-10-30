package com.mainsteps.martiniconecta.viewmodel

import com.mainsteps.martiniconecta.model.Contenido
import com.mainsteps.martiniconecta.repository.CatalogoRepository
import com.mainsteps.martiniconecta.repository.FavoritosDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class FavoritosViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun configurar() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun limpiar() {
        Dispatchers.resetMain()
    }

    @Test
    fun `fallo al alternar favorito muestra error sin cerrar la app`() = runTest {
        val viewModel = FavoritosViewModel(FavoritosDataSourceQueFalla())
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.uiState.collect()
        }

        viewModel.alternarFavorito(CatalogoRepository.obtenerTodos().first())
        advanceUntilIdle()

        assertEquals(
            "No pudimos actualizar tus favoritos. Intenta de nuevo.",
            viewModel.uiState.value.mensajeError,
        )
    }
}

private class FavoritosDataSourceQueFalla : FavoritosDataSource {
    override fun observarFavoritos(): Flow<List<Contenido>> = flowOf(emptyList())

    override suspend fun alternarFavorito(contenido: Contenido) {
        throw RuntimeException("Room")
    }
}
