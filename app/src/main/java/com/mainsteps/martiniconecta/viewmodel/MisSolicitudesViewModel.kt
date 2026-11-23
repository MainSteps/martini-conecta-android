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
import kotlinx.coroutines.CancellationException

sealed interface MisSolicitudesUiState {
    data object Cargando : MisSolicitudesUiState

    data class Exito(
        val solicitudes: List<SolicitudContacto>,
        val edicion: SolicitudContacto? = null,
        val paraEliminar: SolicitudContacto? = null,
        val errorComuna: String? = null,
        val mensaje: String? = null,
        val procesando: Boolean = false,
    ) : MisSolicitudesUiState

    data object Vacio : MisSolicitudesUiState

    data class Error(val mensaje: String) : MisSolicitudesUiState
}

class MisSolicitudesViewModel(
    private val contactoRepository: ContactoRepository,
    private val perfilRepository: PerfilRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow<MisSolicitudesUiState>(MisSolicitudesUiState.Cargando)
    val uiState: StateFlow<MisSolicitudesUiState> = _uiState.asStateFlow()
    private var telefonoConsulta: String? = null

    fun cargar() {
        viewModelScope.launch {
            _uiState.value = MisSolicitudesUiState.Cargando
            val telefonoPerfil = perfilRepository.obtenerPerfil().first()?.telefono?.trim()
            if (telefonoPerfil.isNullOrEmpty()) {
                _uiState.value = MisSolicitudesUiState.Vacio
                return@launch
            }
            val telefonoNormalizado = ContactoValidator.normalizarTelefono(telefonoPerfil)
            telefonoConsulta = telefonoNormalizado
            try {
                val solicitudes = contactoRepository.listarSolicitudes(telefonoNormalizado)
                _uiState.value = if (solicitudes.isEmpty()) {
                    MisSolicitudesUiState.Vacio
                } else {
                    MisSolicitudesUiState.Exito(solicitudes)
                }
            } catch (cancelada: CancellationException) {
                throw cancelada
            } catch (_: Exception) {
                _uiState.value = MisSolicitudesUiState.Error(MENSAJE_ERROR_CARGA)
            }
        }
    }

    fun reintentar() {
        cargar()
    }

    fun onEditar(solicitud: SolicitudContacto) {
        val estado = _uiState.value as? MisSolicitudesUiState.Exito ?: return
        if (estado.procesando) return
        _uiState.value = estado.copy(edicion = solicitud, paraEliminar = null, errorComuna = null, mensaje = null)
    }

    fun onCambiarEdicion(solicitud: SolicitudContacto) {
        val estado = _uiState.value as? MisSolicitudesUiState.Exito ?: return
        if (estado.procesando || estado.edicion?.id != solicitud.id) return
        _uiState.value = estado.copy(edicion = solicitud, errorComuna = null)
    }

    fun onConfirmarEliminar(solicitud: SolicitudContacto) {
        val estado = _uiState.value as? MisSolicitudesUiState.Exito ?: return
        if (estado.procesando) return
        _uiState.value = estado.copy(paraEliminar = solicitud, edicion = null, mensaje = null)
    }

    fun onCancelar() {
        val estado = _uiState.value as? MisSolicitudesUiState.Exito ?: return
        if (!estado.procesando) {
            _uiState.value = estado.copy(edicion = null, paraEliminar = null, errorComuna = null, mensaje = null)
        }
    }

    fun onGuardarEdicion() {
        val estado = _uiState.value as? MisSolicitudesUiState.Exito ?: return
        val solicitud = estado.edicion ?: return
        val error = ContactoValidator.errorComuna(solicitud.comuna)
        if (error != null) {
            _uiState.value = estado.copy(errorComuna = error)
            return
        }
        ejecutarOperacion("No pudimos guardar los cambios. Intenta de nuevo.") {
            val actualizada = contactoRepository.actualizarSolicitud(solicitud.copy(comuna = solicitud.comuna.trim()))
            estado.solicitudes.map { if (it.id == actualizada.id) actualizada else it }
        }
    }

    fun onEliminar(id: Long) {
        val estado = _uiState.value as? MisSolicitudesUiState.Exito ?: return
        if (estado.paraEliminar?.id != id) return
        ejecutarOperacion("No pudimos eliminar la solicitud. Intenta de nuevo.") {
            contactoRepository.eliminarSolicitud(id)
            estado.solicitudes.filterNot { it.id == id }
        }
    }

    private fun ejecutarOperacion(mensajeError: String, operacion: suspend () -> List<SolicitudContacto>) {
        val anterior = _uiState.value as? MisSolicitudesUiState.Exito ?: return
        if (anterior.procesando) return
        _uiState.value = anterior.copy(procesando = true, mensaje = null)
        viewModelScope.launch {
            try {
                val resultado = operacion()
                var mensaje: String? = null
                val solicitudes = try {
                    contactoRepository.listarSolicitudes(telefonoConsulta)
                } catch (cancelada: CancellationException) {
                    throw cancelada
                } catch (_: Exception) {
                    mensaje = "Cambio guardado. No pudimos recargar la lista; vuelve a entrar para actualizarla."
                    resultado
                }
                _uiState.value = if (solicitudes.isEmpty()) MisSolicitudesUiState.Vacio
                else MisSolicitudesUiState.Exito(solicitudes, mensaje = mensaje)
            } catch (cancelada: CancellationException) {
                throw cancelada
            } catch (_: Exception) {
                _uiState.value = anterior.copy(mensaje = mensajeError)
            }
        }
    }

    private companion object {
        const val MENSAJE_ERROR_CARGA =
            "No pudimos cargar tus solicitudes. Revisa tu conexión e intenta de nuevo."
    }
}
