package com.mainsteps.martiniconecta.viewmodel

import com.mainsteps.martiniconecta.data.local.PerfilDao
import com.mainsteps.martiniconecta.data.local.PerfilEntity
import com.mainsteps.martiniconecta.data.local.SolicitudDao
import com.mainsteps.martiniconecta.data.local.SolicitudEntity
import com.mainsteps.martiniconecta.model.Division
import com.mainsteps.martiniconecta.model.ProductoInteres
import com.mainsteps.martiniconecta.repository.ContactoRepository
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
import org.junit.Assert.assertFalse
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ContactoViewModelTest {

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
    fun `fallo al guardar muestra error y conserva formulario`() = runTest {
        val viewModel = ContactoViewModel(
            contactoRepository = ContactoRepository(SolicitudDaoQueFalla()),
            perfilRepository = PerfilRepository(PerfilDaoVacio()),
            ahora = { 1000L },
        )

        advanceUntilIdle()

        viewModel.onNombreChange("Ana Pérez")
        viewModel.onTelefonoChange("+56 9 1234 5678")
        viewModel.onDivisionChange(Division.GELATERIA)
        viewModel.onComunaChange("Maipú")
        viewModel.onProductoChange(ProductoInteres.CHOCOLATE_ARIBA)

        viewModel.onEnviar()
        advanceUntilIdle()

        val estado = viewModel.uiState.value
        assertFalse(estado.enviando)
        assertEquals(
            "No pudimos enviar tu solicitud. Intenta de nuevo.",
            estado.mensajeError,
        )
        assertFalse(estado.enviadoExitoso)
        assertEquals("Ana Pérez", estado.nombre.valor)
        assertEquals("+56 9 1234 5678", estado.telefono.valor)
        assertEquals(Division.GELATERIA, estado.division.valor)
        assertEquals("Maipú", estado.comuna.valor)
        assertEquals(ProductoInteres.CHOCOLATE_ARIBA, estado.producto.valor)
    }
}

private class SolicitudDaoQueFalla : SolicitudDao {
    override suspend fun guardarSolicitud(solicitud: SolicitudEntity): Long {
        throw RuntimeException("Room")
    }

    override fun observarSolicitudes(): Flow<List<SolicitudEntity>> = flowOf(emptyList())
}

private class PerfilDaoVacio : PerfilDao {
    override fun obtenerPerfil(): Flow<PerfilEntity?> = flowOf(null)

    override suspend fun obtenerPerfilActual(): PerfilEntity? = null

    override suspend fun guardarPerfil(perfil: PerfilEntity) {}
}
