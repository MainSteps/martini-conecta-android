package com.mainsteps.martiniconecta.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.mainsteps.martiniconecta.model.Contenido
import com.mainsteps.martiniconecta.repository.CatalogoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class DetailViewModel @JvmOverloads constructor(
    savedStateHandle: SavedStateHandle,
    obtenerContenido: (String) -> Contenido? = CatalogoRepository::obtenerPorId
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        DetailUiState(
            receta = obtenerContenido(
                savedStateHandle.get<String>(DETAIL_ITEM_ID).orEmpty()
            )
        )
    )
    val uiState: StateFlow<DetailUiState> = _uiState.asStateFlow()

    companion object {
        private const val DETAIL_ITEM_ID = "itemId"
    }
}
