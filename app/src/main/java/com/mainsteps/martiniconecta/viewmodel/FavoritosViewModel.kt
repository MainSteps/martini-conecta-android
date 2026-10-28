package com.mainsteps.martiniconecta.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mainsteps.martiniconecta.model.Contenido
import com.mainsteps.martiniconecta.repository.FavoritosDataSource
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class FavoritosViewModel(
    private val favoritosRepository: FavoritosDataSource,
) : ViewModel() {

    val uiState: StateFlow<FavoritosUiState> = favoritosRepository
        .observarFavoritos()
        .map { favoritos -> FavoritosUiState(favoritos) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = FavoritosUiState(),
        )

    fun alternarFavorito(contenido: Contenido) {
        viewModelScope.launch {
            favoritosRepository.alternarFavorito(contenido)
        }
    }
}
