package com.mainsteps.martiniconecta.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mainsteps.martiniconecta.data.local.PerfilEntity
import com.mainsteps.martiniconecta.model.CampoMultiSeleccion
import com.mainsteps.martiniconecta.model.CampoSeleccion
import com.mainsteps.martiniconecta.model.CampoTexto
import com.mainsteps.martiniconecta.model.Division
import com.mainsteps.martiniconecta.model.Interes
import com.mainsteps.martiniconecta.model.PerfilUiState
import com.mainsteps.martiniconecta.model.TamanoNegocio
import com.mainsteps.martiniconecta.repository.PerfilRepository
import com.mainsteps.martiniconecta.viewmodel.validation.PerfilValidator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val perfilRepository: PerfilRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(PerfilUiState())
    val uiState: StateFlow<PerfilUiState> = _uiState.asStateFlow()

    private val _hasProfile = MutableStateFlow<Boolean?>(null)
    val hasProfile: StateFlow<Boolean?> = _hasProfile.asStateFlow()

    private var formularioHidratado = false

    init {
        viewModelScope.launch {
            perfilRepository.obtenerPerfil().collect { entity ->
                _hasProfile.value = entity != null
                if (!formularioHidratado) {
                    formularioHidratado = true
                    if (entity != null) {
                        _uiState.value = entity.toUiState()
                    }
                }
            }
        }
    }

    fun onNombreChange(valor: String) {
        actualizarTexto { state ->
            state.copy(
                nombre = state.nombre.actualizado(valor, PerfilValidator::errorNombre)
            )
        }
    }

    fun onCorreoChange(valor: String) {
        actualizarTexto { state ->
            state.copy(
                correo = state.correo.actualizado(valor, PerfilValidator::errorCorreo)
            )
        }
    }

    fun onTelefonoChange(valor: String) {
        actualizarTexto { state ->
            state.copy(
                telefono = state.telefono.actualizado(valor, PerfilValidator::errorTelefono)
            )
        }
    }

    fun onNombreFocusLost() {
        actualizarTexto { state ->
            state.copy(
                nombre = state.nombre.copy(
                    error = PerfilValidator.errorNombre(state.nombre.valor)
                )
            )
        }
    }

    fun onCorreoFocusLost() {
        actualizarTexto { state ->
            state.copy(
                correo = state.correo.copy(
                    error = PerfilValidator.errorCorreo(state.correo.valor)
                )
            )
        }
    }

    fun onTelefonoFocusLost() {
        actualizarTexto { state ->
            state.copy(
                telefono = state.telefono.copy(
                    error = PerfilValidator.errorTelefono(state.telefono.valor)
                )
            )
        }
    }

    fun onDivisionChange(division: Division) {
        actualizarTexto { state ->
            state.copy(
                division = state.division.copy(valor = division, error = null)
            )
        }
    }

    fun onTamanoChange(tamano: TamanoNegocio) {
        actualizarTexto { state ->
            state.copy(
                tamano = state.tamano.copy(valor = tamano, error = null)
            )
        }
    }

    fun onInteresToggle(interes: Interes) {
        actualizarTexto { state ->
            val seleccionados = state.intereses.seleccionados.toMutableSet()
            if (interes in seleccionados) {
                seleccionados.remove(interes)
            } else {
                seleccionados.add(interes)
            }
            state.copy(
                intereses = state.intereses.copy(
                    seleccionados = seleccionados,
                    error = PerfilValidator.errorIntereses(seleccionados)
                )
            )
        }
    }

    fun onLetraGrandeCambiada(activada: Boolean) {
        _uiState.update { it.copy(letraGrande = activada) }
        viewModelScope.launch {
            persistirPerfilActual()
        }
    }

    fun onGuardar() {
        var valido = false
        var estadoValidado = _uiState.value
        _uiState.update { state ->
            val actualizado = state.copy(
                nombre = state.nombre.copy(
                    error = PerfilValidator.errorNombre(state.nombre.valor)
                ),
                correo = state.correo.copy(
                    error = PerfilValidator.errorCorreo(state.correo.valor)
                ),
                telefono = state.telefono.copy(
                    error = PerfilValidator.errorTelefono(state.telefono.valor)
                ),
                division = state.division.copy(
                    error = PerfilValidator.errorDivision(state.division.valor)
                ),
                tamano = state.tamano.copy(
                    error = PerfilValidator.errorTamano(state.tamano.valor)
                ),
                intereses = state.intereses.copy(
                    error = PerfilValidator.errorIntereses(state.intereses.seleccionados)
                )
            ).conEsValido()
            valido = actualizado.esValido
            estadoValidado = actualizado
            actualizado.copy(guardadoExitoso = false)
        }
        if (!valido) {
            return
        }
        viewModelScope.launch {
            perfilRepository.guardarPerfil(estadoValidado.toEntity())
            _uiState.update { it.copy(guardadoExitoso = true).conEsValido() }
        }
    }

    private fun actualizarTexto(transform: (PerfilUiState) -> PerfilUiState) {
        _uiState.update { transform(it).copy(guardadoExitoso = false).conEsValido() }
    }

    private fun CampoTexto.actualizado(
        valor: String,
        validar: (String) -> String?
    ): CampoTexto {
        val error = if (error != null) {
            validar(valor)
        } else {
            null
        }
        return copy(valor = valor, error = error)
    }

    private fun PerfilUiState.conEsValido(): PerfilUiState {
        return copy(esValido = PerfilValidator.esFormularioValido(this))
    }

    private suspend fun persistirPerfilActual() {
        val state = _uiState.value
        if (PerfilValidator.esFormularioValido(state)) {
            perfilRepository.guardarPerfil(state.toEntity())
            return
        }
        if (_hasProfile.value == true) {
            val entity = perfilRepository.obtenerPerfil().first()
            if (entity != null) {
                perfilRepository.guardarPerfil(entity.copy(letraGrande = state.letraGrande))
            }
        }
    }

    private fun PerfilEntity.toUiState(): PerfilUiState {
        return PerfilUiState(
            nombre = CampoTexto(valor = nombre),
            correo = CampoTexto(valor = email),
            telefono = CampoTexto(valor = telefono.orEmpty()),
            division = CampoSeleccion(valor = division),
            tamano = CampoSeleccion(valor = tamano),
            intereses = CampoMultiSeleccion(seleccionados = intereses),
            letraGrande = letraGrande,
        ).conEsValido()
    }

    private fun PerfilUiState.toEntity(): PerfilEntity {
        val telefonoLimpio = telefono.valor.trim()
        return PerfilEntity(
            id = PerfilEntity.PERFIL_ID,
            nombre = nombre.valor.trim(),
            email = correo.valor.trim(),
            telefono = telefonoLimpio.takeIf { it.isNotEmpty() },
            division = division.valor!!,
            tamano = tamano.valor!!,
            intereses = intereses.seleccionados,
            fotoUri = null,
            letraGrande = letraGrande,
        )
    }
}
