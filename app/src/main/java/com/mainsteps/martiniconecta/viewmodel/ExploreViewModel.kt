package com.mainsteps.martiniconecta.viewmodel

import androidx.lifecycle.ViewModel
import com.mainsteps.martiniconecta.model.Contenido
import com.mainsteps.martiniconecta.model.Division
import com.mainsteps.martiniconecta.repository.CatalogoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ExploreViewModel(
    private val catalogo: List<Contenido> = CatalogoRepository.obtenerTodos()
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        ExploreUiState(recetas = catalogo)
    )
    val uiState: StateFlow<ExploreUiState> = _uiState.asStateFlow()

    fun actualizarBusqueda(busqueda: String) {
        actualizarFiltros(
            busqueda = busqueda,
            division = _uiState.value.divisionSeleccionada
        )
    }

    fun seleccionarDivision(division: Division?) {
        actualizarFiltros(
            busqueda = _uiState.value.busqueda,
            division = division
        )
    }

    fun limpiarFiltros() {
        actualizarFiltros(busqueda = "", division = null)
    }

    private fun actualizarFiltros(
        busqueda: String,
        division: Division?
    ) {
        val busquedaNormalizada = busqueda.trim()
        val recetasFiltradas = catalogo.filter { receta ->
            val coincideTitulo = busquedaNormalizada.isBlank() ||
                receta.titulo.contains(busquedaNormalizada, ignoreCase = true)
            val coincideDivision = division == null || receta.division == division

            coincideTitulo && coincideDivision
        }

        _uiState.value = ExploreUiState(
            busqueda = busqueda,
            divisionSeleccionada = division,
            recetas = recetasFiltradas
        )
    }
}
