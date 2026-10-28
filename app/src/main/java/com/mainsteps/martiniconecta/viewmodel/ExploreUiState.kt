package com.mainsteps.martiniconecta.viewmodel

import com.mainsteps.martiniconecta.model.Contenido
import com.mainsteps.martiniconecta.model.Division

data class ExploreUiState(
    val busqueda: String = "",
    val divisionSeleccionada: Division? = null,
    val recetas: List<Contenido> = emptyList()
)
