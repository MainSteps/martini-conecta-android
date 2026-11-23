package com.mainsteps.martiniconecta.viewmodel

import com.mainsteps.martiniconecta.model.Contenido
import com.mainsteps.martiniconecta.model.Dificultad
import com.mainsteps.martiniconecta.model.Division
import com.mainsteps.martiniconecta.repository.FavoritosDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
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

    private val dispatcher = StandardTestDispatcher()

    @Before
    fun configurarDispatcher() {
        Dispatchers.setMain(dispatcher)
    }

    @After
    fun limpiarDispatcher() {
        Dispatchers.resetMain()
    }

    @Test
    fun `observa favoritos y alterna el estado`() = runTest {
        val dataSource = FavoritosFalso()
        val viewModel = FavoritosViewModel(dataSource)
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.uiState.collect { }
        }
        advanceUntilIdle()

        val receta = recetaPrueba()
        viewModel.alternarFavorito(receta)
        advanceUntilIdle()

        assertEquals(listOf(receta), viewModel.uiState.value.favoritos)
        viewModel.alternarFavorito(receta)
        advanceUntilIdle()
        assertEquals(emptyList<Contenido>(), viewModel.uiState.value.favoritos)
    }

    private fun recetaPrueba() = Contenido(
        id = "gelato",
        titulo = "Gelato",
        descripcion = "Receta de prueba",
        division = Division.GELATERIA,
        categoria = "Postre",
        dificultad = Dificultad.BASICO,
        productosMartini = listOf("Ariba"),
        rindePorciones = 4,
        ingredientes = emptyList(),
        pasos = listOf("Mezclar"),
        imagenResId = 0,
        esVegano = false,
    )
}

private class FavoritosFalso : FavoritosDataSource {
    private val estado = MutableStateFlow<List<Contenido>>(emptyList())

    override fun observarFavoritos(): Flow<List<Contenido>> = estado

    override suspend fun alternarFavorito(contenido: Contenido) {
        estado.value = if (estado.value.any { it.id == contenido.id }) {
            estado.value.filterNot { it.id == contenido.id }
        } else {
            estado.value + contenido
        }
    }
}
