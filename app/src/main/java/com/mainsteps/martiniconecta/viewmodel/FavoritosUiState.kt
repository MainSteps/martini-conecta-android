package com.mainsteps.martiniconecta.viewmodel

import com.mainsteps.martiniconecta.model.Contenido

data class FavoritosUiState(
    val favoritos: List<Contenido> = emptyList(),
) {
    val idsFavoritos: Set<String>
        get() = favoritos.mapTo(mutableSetOf(), Contenido::id)
}
