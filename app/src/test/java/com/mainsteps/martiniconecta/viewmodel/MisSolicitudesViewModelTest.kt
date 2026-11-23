package com.mainsteps.martiniconecta.viewmodel

import com.mainsteps.martiniconecta.data.local.PerfilDao
import com.mainsteps.martiniconecta.data.local.PerfilEntity
import com.mainsteps.martiniconecta.model.Division
import com.mainsteps.martiniconecta.model.EstadoSolicitud
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

    @Test
    fun `editar guarda cambios y recarga solicitudes`() = runTest {
        val (api, vm) = cargarUnaSolicitud()
        val original = (vm.uiState.value as MisSolicitudesUiState.Exito).solicitudes.single()
        vm.onEditar(original)
        vm.onCambiarEdicion(original.copy(comuna = " Ñuñoa ", producto = ProductoInteres.BRAVO_CREM, estado = EstadoSolicitud.CONTACTADO))

        vm.onGuardarEdicion()
        advanceUntilIdle()

        val estado = vm.uiState.value as MisSolicitudesUiState.Exito
        assertEquals("Ñuñoa", estado.solicitudes.single().comuna)
        assertEquals(ProductoInteres.BRAVO_CREM, estado.solicitudes.single().producto)
        assertEquals(EstadoSolicitud.CONTACTADO, estado.solicitudes.single().estado)
        assertEquals(null, estado.edicion)
        assertEquals(2, api.vecesListado)
        assertEquals("+56 9 1234 5678", api.ultimoTelefonoListado)
    }

    @Test
    fun `comuna invalida no envia put`() = runTest {
        val (api, vm) = cargarUnaSolicitud()
        val original = (vm.uiState.value as MisSolicitudesUiState.Exito).solicitudes.single()
        vm.onEditar(original.copy(comuna = " "))
        vm.onGuardarEdicion()
        advanceUntilIdle()

        assertEquals("Ingresa tu comuna", (vm.uiState.value as MisSolicitudesUiState.Exito).errorComuna)
        assertEquals(null, api.ultimoIdActualizado)
    }

    @Test
    fun `eliminar confirmado quita el elemento y recarga`() = runTest {
        val (api, vm) = cargarUnaSolicitud()
        val solicitud = (vm.uiState.value as MisSolicitudesUiState.Exito).solicitudes.single()
        vm.onConfirmarEliminar(solicitud)
        vm.onEliminar(solicitud.id)
        advanceUntilIdle()

        assertEquals(MisSolicitudesUiState.Vacio, vm.uiState.value)
        assertEquals(solicitud.id, api.ultimoIdEliminado)
        assertEquals(2, api.vecesListado)
    }

    @Test
    fun `cancelar eliminacion no llama delete`() = runTest {
        val (api, vm) = cargarUnaSolicitud()
        val solicitud = (vm.uiState.value as MisSolicitudesUiState.Exito).solicitudes.single()
        vm.onConfirmarEliminar(solicitud)
        vm.onCancelar()
        vm.onEliminar(solicitud.id)
        advanceUntilIdle()

        assertEquals(null, api.ultimoIdEliminado)
        assertEquals(1, (vm.uiState.value as MisSolicitudesUiState.Exito).solicitudes.size)
    }

    @Test
    fun `put fallido conserva lista y permite reintentar`() = runTest {
        val (api, vm) = cargarUnaSolicitud()
        val solicitud = (vm.uiState.value as MisSolicitudesUiState.Exito).solicitudes.single()
        api.fallarAlActualizar = true
        vm.onEditar(solicitud.copy(comuna = "Ñuñoa"))
        vm.onGuardarEdicion()
        advanceUntilIdle()

        val estado = vm.uiState.value as MisSolicitudesUiState.Exito
        assertEquals(listOf(solicitud), estado.solicitudes)
        assertTrue(estado.mensaje != null)
        assertEquals(false, estado.procesando)
        api.fallarAlActualizar = false
        vm.onGuardarEdicion()
        advanceUntilIdle()
        assertEquals("Ñuñoa", (vm.uiState.value as MisSolicitudesUiState.Exito).solicitudes.single().comuna)
    }

    @Test
    fun `delete fallido conserva lista y muestra error`() = runTest {
        val (api, vm) = cargarUnaSolicitud()
        val solicitud = (vm.uiState.value as MisSolicitudesUiState.Exito).solicitudes.single()
        api.fallarAlEliminar = true
        vm.onConfirmarEliminar(solicitud)
        vm.onEliminar(solicitud.id)
        advanceUntilIdle()

        val estado = vm.uiState.value as MisSolicitudesUiState.Exito
        assertEquals(listOf(solicitud), estado.solicitudes)
        assertTrue(estado.mensaje != null)
        assertEquals(false, estado.procesando)
    }

    @Test
    fun `si falla recarga tras put conserva el cambio guardado`() = runTest {
        val (api, vm) = cargarUnaSolicitud()
        val solicitud = (vm.uiState.value as MisSolicitudesUiState.Exito).solicitudes.single()
        api.fallarAlListar = true
        vm.onEditar(solicitud.copy(comuna = "Ñuñoa"))
        vm.onGuardarEdicion()
        advanceUntilIdle()

        val estado = vm.uiState.value as MisSolicitudesUiState.Exito
        assertEquals("Ñuñoa", estado.solicitudes.single().comuna)
        assertTrue(estado.mensaje != null)
        assertEquals(null, estado.edicion)
    }

    @Test
    fun `delete http fallido conserva solicitud y permite reintentar`() = runTest {
        val (api, vm) = cargarUnaSolicitud()
        val solicitud = (vm.uiState.value as MisSolicitudesUiState.Exito).solicitudes.single()
        api.errorHttpEliminar = true
        vm.onConfirmarEliminar(solicitud)
        vm.onEliminar(solicitud.id)
        advanceUntilIdle()
        assertEquals(listOf(solicitud), (vm.uiState.value as MisSolicitudesUiState.Exito).solicitudes)
        assertTrue((vm.uiState.value as MisSolicitudesUiState.Exito).mensaje != null)

        api.errorHttpEliminar = false
        vm.onEliminar(solicitud.id)
        advanceUntilIdle()
        assertEquals(MisSolicitudesUiState.Vacio, vm.uiState.value)
    }

    @Test
    fun `guardar dos veces mientras procesa solo recarga una vez`() = runTest {
        val (api, vm) = cargarUnaSolicitud()
        val solicitud = (vm.uiState.value as MisSolicitudesUiState.Exito).solicitudes.single()
        vm.onEditar(solicitud.copy(comuna = "Ñuñoa"))
        vm.onGuardarEdicion()
        vm.onGuardarEdicion()
        vm.onCancelar()
        advanceUntilIdle()

        assertEquals(2, api.vecesListado)
        assertEquals("Ñuñoa", (vm.uiState.value as MisSolicitudesUiState.Exito).solicitudes.single().comuna)
    }

    private suspend fun cargarUnaSolicitud(): Pair<SolicitudApiFalsa, MisSolicitudesViewModel> {
        val api = SolicitudApiFalsa()
        api.crear(solicitudEjemplo)
        val vm = viewModelConPerfil(ContactoRepository(api), "+56 9 1234 5678")
        vm.cargar()
        testDispatcher.scheduler.advanceUntilIdle()
        return api to vm
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
