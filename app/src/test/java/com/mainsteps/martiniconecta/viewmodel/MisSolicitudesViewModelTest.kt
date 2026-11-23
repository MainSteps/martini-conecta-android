package com.mainsteps.martiniconecta.viewmodel

import com.mainsteps.martiniconecta.data.local.PerfilDao
import com.mainsteps.martiniconecta.data.local.PerfilEntity
import com.mainsteps.martiniconecta.model.Division
import com.mainsteps.martiniconecta.model.Interes
import com.mainsteps.martiniconecta.model.ProductoInteres
import com.mainsteps.martiniconecta.model.SolicitudContacto
import com.mainsteps.martiniconecta.model.TamanoNegocio
import com.mainsteps.martiniconecta.repository.ContactoRepository
import com.mainsteps.martiniconecta.repository.PerfilRepository
import com.mainsteps.martiniconecta.repository.SolicitudApiFalsa
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
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MisSolicitudesViewModelTest {

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
    fun `con datos muestra exito`() = runTest {
        val api = SolicitudApiFalsa()
        val repository = ContactoRepository(api)
        repository.guardarSolicitud(solicitudEjemplo)
        val viewModel = viewModelConPerfil(repository, telefonoPerfil = "+56 9 1234 5678")

        viewModel.cargar()
        advanceUntilIdle()

        val estado = viewModel.uiState.value
        assertTrue(estado is MisSolicitudesUiState.Exito)
        assertEquals(1, (estado as MisSolicitudesUiState.Exito).solicitudes.size)
    }

    @Test
    fun `lista vacia muestra vacio`() = runTest {
        val viewModel = viewModelConPerfil(
            ContactoRepository(SolicitudApiFalsa()),
            telefonoPerfil = "+56 9 1234 5678",
        )

        viewModel.cargar()
        advanceUntilIdle()

        assertEquals(MisSolicitudesUiState.Vacio, viewModel.uiState.value)
    }

    @Test
    fun `sin telefono en perfil muestra vacio`() = runTest {
        val api = SolicitudApiFalsa()
        ContactoRepository(api).guardarSolicitud(solicitudEjemplo)
        val viewModel = viewModelConPerfil(
            ContactoRepository(api),
            telefonoPerfil = null,
        )

        viewModel.cargar()
        advanceUntilIdle()

        assertEquals(MisSolicitudesUiState.Vacio, viewModel.uiState.value)
    }

    @Test
    fun `solo muestra solicitudes del telefono del perfil`() = runTest {
        val api = SolicitudApiFalsa()
        val repository = ContactoRepository(api)
        api.crear(solicitudEjemplo)
        api.crear(
            solicitudEjemplo.copy(
                nombre = "Otra persona",
                telefono = "+56 9 9999 8888",
            ),
        )
        val viewModel = viewModelConPerfil(repository, telefonoPerfil = "+56 9 1234 5678")

        viewModel.cargar()
        advanceUntilIdle()

        val estado = viewModel.uiState.value as MisSolicitudesUiState.Exito
        assertEquals("+56 9 1234 5678", api.ultimoTelefonoListado)
        assertEquals(1, estado.solicitudes.size)
        assertEquals("+56 9 1234 5678", estado.solicitudes.single().telefono)
    }

    @Test
    fun `fallo al listar muestra error`() = runTest {
        val viewModel = viewModelConPerfil(
            ContactoRepository(SolicitudApiFalsa(fallarAlListar = true)),
            telefonoPerfil = "+56 9 1234 5678",
        )

        viewModel.cargar()
        advanceUntilIdle()

        val estado = viewModel.uiState.value
        assertTrue(estado is MisSolicitudesUiState.Error)
    }

    @Test
    fun `reintentar tras error vuelve a cargar`() = runTest {
        val api = SolicitudApiFalsa(fallarAlListar = true)
        val viewModel = viewModelConPerfil(ContactoRepository(api), telefonoPerfil = "+56 9 1234 5678")

        viewModel.cargar()
        advanceUntilIdle()
        assertTrue(viewModel.uiState.value is MisSolicitudesUiState.Error)

        api.fallarAlListar = false
        api.crear(solicitudEjemplo)
        viewModel.reintentar()
        advanceUntilIdle()

        assertTrue(viewModel.uiState.value is MisSolicitudesUiState.Exito)
    }

    private fun viewModelConPerfil(
        contactoRepository: ContactoRepository,
        telefonoPerfil: String?,
    ): MisSolicitudesViewModel {
        return MisSolicitudesViewModel(
            contactoRepository,
            PerfilRepository(perfilDaoConTelefono(telefonoPerfil)),
        )
    }

    private val solicitudEjemplo = SolicitudContacto(
        nombre = "Ana Pérez",
        telefono = "+56 9 1234 5678",
        division = Division.GELATERIA,
        comuna = "Maipú",
        producto = ProductoInteres.CHOCOLATE_ARIBA,
        creadaEn = 1000,
    )
}

private fun perfilDaoConTelefono(telefono: String?): PerfilDao {
    val perfil = PerfilEntity(
        nombre = "Ana",
        email = "ana@ejemplo.cl",
        telefono = telefono,
        division = Division.GELATERIA,
        tamano = TamanoNegocio.SOLO_YO,
        intereses = setOf(Interes.RECETAS),
        fotoUri = null,
    )
    return object : PerfilDao {
        override fun obtenerPerfil(): Flow<PerfilEntity?> = flowOf(perfil)

        override suspend fun obtenerPerfilActual(): PerfilEntity? = perfil

        override suspend fun guardarPerfil(perfil: PerfilEntity) {}
    }
}