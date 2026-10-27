package com.mainsteps.martiniconecta.viewmodel

import com.mainsteps.martiniconecta.model.Contenido

data class DetailUiState(
    val receta: Contenido? = null
) {
    val recetaEncontrada: Boolean
        get() = receta != null
}
