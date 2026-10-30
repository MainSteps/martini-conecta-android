package com.mainsteps.martiniconecta.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mainsteps.martiniconecta.model.Contenido
import com.mainsteps.martiniconecta.repository.FavoritosDataSource
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class FavoritosViewModel(
    private val favoritosRepository: FavoritosDataSource,
) : ViewModel() {

    private val mensajeError = MutableStateFlow<String?>(null)

    val uiState: StateFlow<FavoritosUiState> = combine(
        favoritosRepository.observarFavoritos().catch { error ->
            if (error is CancellationException) throw error
            mensajeError.value = MENSAJE_ERROR_CARGA
            emit(emptyList())
        },
        mensajeError,
    ) { favoritos, error ->
        FavoritosUiState(favoritos = favoritos, mensajeError = error)
    }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = FavoritosUiState(),
        )

    fun alternarFavorito(contenido: Contenido) {
        viewModelScope.launch {
            try {
                favoritosRepository.alternarFavorito(contenido)
                mensajeError.value = null
            } catch (e: CancellationException) {
                throw e
            } catch (_: Exception) {
                mensajeError.value = MENSAJE_ERROR_FAVORITO
            }
        }
    }

    private companion object {
        const val MENSAJE_ERROR_FAVORITO = "No pudimos actualizar tus favoritos. Intenta de nuevo."
        const val MENSAJE_ERROR_CARGA = "No pudimos cargar tus favoritos. Intenta reiniciar la app."
    }
}
