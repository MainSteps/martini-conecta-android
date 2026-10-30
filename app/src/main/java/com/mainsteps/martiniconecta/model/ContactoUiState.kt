package com.mainsteps.martiniconecta.model

data class ContactoUiState(
    val nombre: CampoTexto = CampoTexto(),
    val telefono: CampoTexto = CampoTexto(valor = "+56 9 "),
    val division: CampoSeleccion<Division> = CampoSeleccion(),
    val comuna: CampoTexto = CampoTexto(),
    val producto: CampoSeleccion<ProductoInteres> = CampoSeleccion(),
    val esValido: Boolean = false,
    val enviando: Boolean = false,
    val enviadoExitoso: Boolean = false,
    val intentoEnvio: Boolean = false,
    val mensajeError: String? = null,
)
