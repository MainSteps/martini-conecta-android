package com.mainsteps.martiniconecta.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mainsteps.martiniconecta.data.local.PerfilEntity
import com.mainsteps.martiniconecta.model.Contenido
import com.mainsteps.martiniconecta.model.Division
import com.mainsteps.martiniconecta.repository.CatalogoRepository
import com.mainsteps.martiniconecta.repository.PerfilRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch

sealed interface InicioUiState {
    data object Cargando : InicioUiState

    data object SinPerfil : InicioUiState

    data class ConRecetas(
        val nombre: String,
        val titulo: String,
        val recetas: List<Contenido>,
    ) : InicioUiState

    data class Vacio(
        val nombre: String,
        val titulo: String,
    ) : InicioUiState
}

class InicioViewModel @JvmOverloads constructor(
    private val perfilRepository: PerfilRepository,
    private val obtenerCatalogo: () -> List<Contenido> = CatalogoRepository::obtenerTodos,
) : ViewModel() {

    private val _uiState = MutableStateFlow<InicioUiState>(InicioUiState.Cargando)
    val uiState: StateFlow<InicioUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            combine(
                perfilRepository.obtenerPerfil(),
                flowOf(obtenerCatalogo()),
            ) { perfil, catalogo ->
                mapearEstado(perfil, catalogo)
            }.collect { estado ->
                _uiState.value = estado
            }
        }
    }

    private fun mapearEstado(
        perfil: PerfilEntity?,
        catalogo: List<Contenido>,
    ): InicioUiState {
        if (perfil == null) {
            return InicioUiState.SinPerfil
        }

        val titulo = tituloColeccion(perfil.division)
        val recetas = catalogo.filter { receta -> receta.division == perfil.division }

        return if (recetas.isEmpty()) {
            InicioUiState.Vacio(
                nombre = perfil.nombre,
                titulo = titulo,
            )
        } else {
            InicioUiState.ConRecetas(
                nombre = perfil.nombre,
                titulo = titulo,
                recetas = recetas,
            )
        }
    }

    private fun tituloColeccion(division: Division): String = when (division) {
        Division.PANADERIA_PASTELERIA -> "Para tu panadería"
        Division.GELATERIA -> "Para tu heladería"
        Division.FOOD_SERVICE -> "Para tu servicio gastronómico"
        Division.RETAIL -> "Para tu negocio retail"
    }
}
