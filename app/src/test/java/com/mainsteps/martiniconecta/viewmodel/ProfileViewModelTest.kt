package com.mainsteps.martiniconecta.viewmodel

import com.mainsteps.martiniconecta.data.local.PerfilDao
import com.mainsteps.martiniconecta.data.local.PerfilEntity
import com.mainsteps.martiniconecta.model.Division
import com.mainsteps.martiniconecta.model.Interes
import com.mainsteps.martiniconecta.model.TamanoNegocio
import com.mainsteps.martiniconecta.repository.PerfilRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ProfileViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun configurar() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun limpiar() {
        Dispatchers.resetMain()
    }

    @Test
    fun `fallo al guardar foto restaura la anterior y muestra error`() = runTest {
        val perfil = PerfilEntity(
            nombre = "Ana Pérez",
            email = "ana@example.com",
            telefono = null,
            division = Division.GELATERIA,
            tamano = TamanoNegocio.SOLO_YO,
            intereses = setOf(Interes.RECETAS),
            fotoUri = "foto-anterior",
        )
        val viewModel = ProfileViewModel(
            PerfilRepository(PerfilDaoQueFallaAlGuardar(perfil)),
        )
        advanceUntilIdle()

        viewModel.onFotoSeleccionada("foto-nueva")
        advanceUntilIdle()

        assertEquals("foto-anterior", viewModel.uiState.value.fotoUri)
        assertEquals(
            "No pudimos guardar la foto. Intenta de nuevo.",
            viewModel.uiState.value.mensajeError,
        )
    }
}

private class PerfilDaoQueFallaAlGuardar(
    private val perfil: PerfilEntity,
) : PerfilDao {
    override fun obtenerPerfil(): Flow<PerfilEntity?> = flowOf(perfil)

    override suspend fun obtenerPerfilActual(): PerfilEntity = perfil

    override suspend fun guardarPerfil(perfil: PerfilEntity) {
        throw RuntimeException("Room")
    }
}
