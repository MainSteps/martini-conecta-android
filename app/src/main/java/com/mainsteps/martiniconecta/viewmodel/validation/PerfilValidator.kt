package com.mainsteps.martiniconecta.viewmodel.validation

import android.util.Patterns
import com.mainsteps.martiniconecta.model.Division
import com.mainsteps.martiniconecta.model.Interes
import com.mainsteps.martiniconecta.model.PerfilUiState
import com.mainsteps.martiniconecta.model.TamanoNegocio

object PerfilValidator {

    fun errorNombre(valor: String): String? {
        if (valor.trim().length < 2) {
            return "Escribe al menos 2 letras"
        }
        return null
    }

    fun errorCorreo(valor: String): String? {
        val limpio = valor.trim()
        if (limpio.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(limpio).matches()) {
            return "Correo no válido"
        }
        return null
    }

    fun errorTelefono(valor: String): String? {
        if (valor.isBlank()) {
            return null
        }
        val sinEspacios = valor.replace(" ", "")
        if (!Regex("^\\+569\\d{8}$").matches(sinEspacios)) {
            return "Usa +56 9 y 8 dígitos, o déjalo vacío"
        }
        return null
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
