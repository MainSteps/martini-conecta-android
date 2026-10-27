package com.mainsteps.martiniconecta.viewmodel

import androidx.lifecycle.SavedStateHandle
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class DetailViewModelTest {

    @Test
    fun `id existente carga la receta correspondiente`() {
        val viewModel = DetailViewModel(
            savedStateHandle = SavedStateHandle(
                mapOf("itemId" to "gelato-chocolate-intenso")
            )
        )

        assertTrue(viewModel.uiState.value.recetaEncontrada)
        assertEquals(
            "Gelato de chocolate intenso",
            viewModel.uiState.value.receta?.titulo
        )
    }

    @Test
    fun `id inexistente produce un estado sin receta`() {
        val viewModel = DetailViewModel(
            savedStateHandle = SavedStateHandle(
                mapOf("itemId" to "receta-inexistente")
            )
        )

        assertFalse(viewModel.uiState.value.recetaEncontrada)
        assertEquals(null, viewModel.uiState.value.receta)
    }
}
