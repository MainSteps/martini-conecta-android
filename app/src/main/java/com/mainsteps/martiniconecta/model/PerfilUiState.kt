package com.mainsteps.martiniconecta.model

data class CampoTexto(
    val valor: String = "",
    val error: String? = null
)

data class CampoSeleccion<T>(
    val valor: T? = null,
    val error: String? = null
)

data class CampoMultiSeleccion<T>(
    val seleccionados: Set<T> = emptySet(),
    val error: String? = null
)

data class PerfilUiState(
    val nombre: CampoTexto = CampoTexto(),
    val correo: CampoTexto = CampoTexto(),
    val telefono: CampoTexto = CampoTexto(),
    val division: CampoSeleccion<Division> = CampoSeleccion(),
    val tamano: CampoSeleccion<TamanoNegocio> = CampoSeleccion(),
    val intereses: CampoMultiSeleccion<Interes> = CampoMultiSeleccion(),
    val fotoUri: String? = null,
    val esValido: Boolean = false,
    val guardadoExitoso: Boolean = false,
    val letraGrande: Boolean = false,
)
