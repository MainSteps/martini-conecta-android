package com.mainsteps.martiniconecta.viewmodel.validation

import com.mainsteps.martiniconecta.model.ContactoUiState
import com.mainsteps.martiniconecta.model.Division
import com.mainsteps.martiniconecta.model.ProductoInteres

object ContactoValidator {
    private val telefonoRegex = Regex("^(?:\\+?56)?9\\d{8}$")
    private val separadoresTelefonoRegex = Regex("[\\s()-]")

    fun errorNombre(valor: String): String? {
        return if (valor.trim().length >= 2) null else "Ingresa tu nombre"
    }

    fun errorTelefono(valor: String): String? {
        return if (telefonoRegex.matches(telefonoSinSeparadores(valor))) {
            null
        } else {
            "Ingresa un celular chileno de 9 dígitos"
        }
    }

    fun normalizarTelefono(valor: String): String {
        val numero = telefonoSinSeparadores(valor)
        require(telefonoRegex.matches(numero))
        val numeroNacional = numero.removePrefix("+56").removePrefix("56")
        return "+56 9 ${numeroNacional.substring(1, 5)} ${numeroNacional.substring(5, 9)}"
    }

    fun errorDivision(valor: Division?): String? {
        return if (valor == null) "Elige una división" else null
    }

    fun errorComuna(valor: String): String? {
        return if (valor.trim().length >= 2) null else "Ingresa tu comuna"
    }

    fun errorProducto(valor: ProductoInteres?): String? {
        return if (valor == null) "Elige un producto" else null
    }

    fun esFormularioValido(state: ContactoUiState): Boolean {
        return errorNombre(state.nombre.valor) == null &&
            errorTelefono(state.telefono.valor) == null &&
            errorDivision(state.division.valor) == null &&
            errorComuna(state.comuna.valor) == null &&
            errorProducto(state.producto.valor) == null
    }

    private fun telefonoSinSeparadores(valor: String): String {
        return valor.replace(separadoresTelefonoRegex, "")
    }
}
