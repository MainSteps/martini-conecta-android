package com.mainsteps.martiniconecta.viewmodel

import com.mainsteps.martiniconecta.model.Division
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class ExploreViewModelTest {

    @Test
    fun `al iniciar muestra todo el catalogo`() {
        val viewModel = ExploreViewModel()

        assertEquals(12, viewModel.uiState.value.recetas.size)
    }

    @Test
    fun `busqueda filtra por titulo sin distinguir mayusculas`() {
        val viewModel = ExploreViewModel()

        viewModel.actualizarBusqueda("GELATO")

        assertEquals(2, viewModel.uiState.value.recetas.size)
        assertTrue(
            viewModel.uiState.value.recetas.all {
                it.titulo.contains("gelato", ignoreCase = true)
            }
        )
    }

    @Test
    fun `division y busqueda se aplican al mismo tiempo`() {
        val viewModel = ExploreViewModel()

        viewModel.seleccionarDivision(Division.RETAIL)
        viewModel.actualizarBusqueda("cacao")

        assertEquals(2, viewModel.uiState.value.recetas.size)
        assertTrue(
            viewModel.uiState.value.recetas.all {
                it.division == Division.RETAIL &&
                    it.titulo.contains("cacao", ignoreCase = true)
            }
        )
    }

    @Test
    fun `limpiar filtros restaura el catalogo completo`() {
        val viewModel = ExploreViewModel()

        viewModel.seleccionarDivision(Division.GELATERIA)
        viewModel.actualizarBusqueda("inexistente")
        viewModel.limpiarFiltros()

        assertEquals("", viewModel.uiState.value.busqueda)
        assertEquals(null, viewModel.uiState.value.divisionSeleccionada)
        assertEquals(12, viewModel.uiState.value.recetas.size)
    }
}
