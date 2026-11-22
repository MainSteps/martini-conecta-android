package com.mainsteps.martiniconecta.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mainsteps.martiniconecta.model.SolicitudContacto
import com.mainsteps.martiniconecta.repository.ContactoRepository
import com.mainsteps.martiniconecta.repository.PerfilRepository
import com.mainsteps.martiniconecta.viewmodel.validation.ContactoValidator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

sealed interface MisSolicitudesUiState {
    data object Cargando : MisSolicitudesUiState

    data class Exito(val solicitudes: List<SolicitudContacto>) : MisSolicitudesUiState

    data object Vacio : MisSolicitudesUiState

    data class Error(val mensaje: String) : MisSolicitudesUiState
}

class MisSolicitudesViewModel(
    private val contactoRepository: ContactoRepository,
    private val perfilRepository: PerfilRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow<MisSolicitudesUiState>(MisSolicitudesUiState.Cargando)
    val uiState: StateFlow<MisSolicitudesUiState> = _uiState.asStateFlow()

    fun cargar() {
        viewModelScope.launch {
            _uiState.value = MisSolicitudesUiState.Cargando
            val telefonoPerfil = perfilRepository.obtenerPerfil().first()?.telefono?.trim()
            if (telefonoPerfil.isNullOrEmpty()) {
                _uiState.value = MisSolicitudesUiState.Vacio
                return@launch
            }
            val telefonoNormalizado = ContactoValidator.normalizarTelefono(telefonoPerfil)
            try {
                val solicitudes = contactoRepository.listarSolicitudes(telefonoNormalizado)
                _uiState.value = if (solicitudes.isEmpty()) {
                    MisSolicitudesUiState.Vacio
                } else {
                    MisSolicitudesUiState.Exito(solicitudes)
                }
            } catch (_: Exception) {
                _uiState.value = MisSolicitudesUiState.Error(MENSAJE_ERROR_CARGA)
            }
        }
    }

    fun reintentar() {
        cargar()
    }

    private companion object {
        const val MENSAJE_ERROR_CARGA =
            "No pudimos cargar tus solicitudes. Revisa tu conexión e intenta de nuevo."
    }
}