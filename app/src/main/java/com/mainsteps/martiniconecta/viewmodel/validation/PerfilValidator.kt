package com.mainsteps.martiniconecta.viewmodel.validation

import com.mainsteps.martiniconecta.model.Division
import com.mainsteps.martiniconecta.model.Interes
import com.mainsteps.martiniconecta.model.PerfilUiState
import com.mainsteps.martiniconecta.model.TamanoNegocio

object PerfilValidator {

    private val correoRegex = Regex(
        "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$",
    )

    fun errorNombre(valor: String): String? {
        if (valor.trim().count { it.isLetter() } < 2) {
            return "Escribe al menos 2 letras"
        }
        return null
    }

    fun errorCorreo(valor: String): String? {
        val limpio = valor.trim()
        if (limpio.isEmpty() || !correoRegex.matches(limpio)) {
            return "Correo no válido"
        }
        return null
    }

    fun errorTelefono(valor: String): String? {
        if (valor.isBlank()) {
            return null
        }
        return if (ContactoValidator.errorTelefono(valor) == null) {
            null
        } else {
            "Ingresa un telefono chileno de 9 dígitos, o déjalo vacío"
        }
    }

    fun normalizarTelefono(valor: String): String? {
        val limpio = valor.trim()
        if (limpio.isEmpty()) {
            return null
        }
        return ContactoValidator.normalizarTelefono(limpio)
    }

    fun errorDivision(division: Division?): String? {
        if (division == null) {
            return "Elige una división"
        }
        return null
    }

    fun errorTamano(tamano: TamanoNegocio?): String? {
        if (tamano == null) {
            return "Elige el tamaño de tu negocio"
        }
        return null
    }

    fun errorIntereses(seleccionados: Set<Interes>): String? {
        if (seleccionados.isEmpty()) {
            return "Elige al menos un interés"
        }
        return null
    }

    fun esFormularioValido(state: PerfilUiState): Boolean {
        return errorNombre(state.nombre.valor) == null &&
            errorCorreo(state.correo.valor) == null &&
            errorTelefono(state.telefono.valor) == null &&
            errorDivision(state.division.valor) == null &&
            errorTamano(state.tamano.valor) == null &&
            errorIntereses(state.intereses.seleccionados) == null
    }
}
