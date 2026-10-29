package com.mainsteps.martiniconecta.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mainsteps.martiniconecta.model.CampoSeleccion
import com.mainsteps.martiniconecta.model.CampoTexto
import com.mainsteps.martiniconecta.model.ContactoUiState
import com.mainsteps.martiniconecta.model.Division
import com.mainsteps.martiniconecta.model.ProductoInteres
import com.mainsteps.martiniconecta.model.SolicitudContacto
import com.mainsteps.martiniconecta.repository.ContactoRepository
import com.mainsteps.martiniconecta.repository.PerfilRepository
import com.mainsteps.martiniconecta.viewmodel.validation.ContactoValidator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ContactoViewModel(
    private val contactoRepository: ContactoRepository,
    private val perfilRepository: PerfilRepository,
    private val ahora: () -> Long = System::currentTimeMillis,
) : ViewModel() {

    private val _uiState = MutableStateFlow(ContactoUiState())
    val uiState: StateFlow<ContactoUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            val perfil = perfilRepository.obtenerPerfil().first()
            if (perfil != null) {
                _uiState.update { state ->
                    state.copy(
                        nombre = CampoTexto(valor = perfil.nombre),
                        telefono = CampoTexto(valor = perfil.telefono ?: "+56 9 "),
                        division = CampoSeleccion(valor = perfil.division),
                    ).conValidez()
                }
            }
        }
    }

    fun onNombreChange(valor: String) = actualizarTexto {
        it.copy(nombre = it.nombre.actualizado(valor, ContactoValidator::errorNombre))
    }

    fun onTelefonoChange(valor: String) = actualizarTexto {
        it.copy(telefono = it.telefono.actualizado(valor, ContactoValidator::errorTelefono))
    }

    fun onComunaChange(valor: String) = actualizarTexto {
        it.copy(comuna = it.comuna.actualizado(valor, ContactoValidator::errorComuna))
    }

    fun onNombreFocusLost() = actualizarTexto {
        it.copy(nombre = it.nombre.copy(error = ContactoValidator.errorNombre(it.nombre.valor)))
    }

    fun onTelefonoFocusLost() = actualizarTexto {
        it.copy(telefono = it.telefono.copy(error = ContactoValidator.errorTelefono(it.telefono.valor)))
    }

    fun onComunaFocusLost() = actualizarTexto {
        it.copy(comuna = it.comuna.copy(error = ContactoValidator.errorComuna(it.comuna.valor)))
    }

    fun onDivisionChange(valor: Division) = actualizarTexto {
        it.copy(division = CampoSeleccion(valor = valor))
    }

    fun onProductoChange(valor: ProductoInteres) = actualizarTexto {
        it.copy(producto = CampoSeleccion(valor = valor))
    }

    fun onEnviar() {
        val validado = _uiState.value.validadoCompleto()
        _uiState.value = validado.copy(enviadoExitoso = false, intentoEnvio = true)
        if (!validado.esValido || validado.enviando) return

        _uiState.update { it.copy(enviando = true) }
        viewModelScope.launch {
            contactoRepository.guardarSolicitud(validado.toModel())
            _uiState.update { it.copy(enviando = false, enviadoExitoso = true) }
        }
    }

    private fun actualizarTexto(transformar: (ContactoUiState) -> ContactoUiState) {
        _uiState.update { transformar(it).copy(enviadoExitoso = false).conValidez() }
    }

    private fun CampoTexto.actualizado(valor: String, validar: (String) -> String?): CampoTexto {
        return copy(valor = valor, error = if (error != null) validar(valor) else null)
    }

    private fun ContactoUiState.validadoCompleto(): ContactoUiState {
        return copy(
            nombre = nombre.copy(error = ContactoValidator.errorNombre(nombre.valor)),
            telefono = telefono.copy(error = ContactoValidator.errorTelefono(telefono.valor)),
            division = division.copy(error = ContactoValidator.errorDivision(division.valor)),
            comuna = comuna.copy(error = ContactoValidator.errorComuna(comuna.valor)),
            producto = producto.copy(error = ContactoValidator.errorProducto(producto.valor)),
        ).conValidez()
    }

    private fun ContactoUiState.conValidez(): ContactoUiState {
        return copy(esValido = ContactoValidator.esFormularioValido(this))
    }

    private fun ContactoUiState.toModel(): SolicitudContacto {
        return SolicitudContacto(
            nombre = nombre.valor.trim(),
            telefono = ContactoValidator.normalizarTelefono(telefono.valor),
            division = requireNotNull(division.valor),
            comuna = comuna.valor.trim(),
            producto = requireNotNull(producto.valor),
            creadaEn = ahora(),
        )
    }
}
