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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ProfileViewModelTest {

    private val dispatcher = StandardTestDispatcher()

    @Before
    fun configurarDispatcher() {
        Dispatchers.setMain(dispatcher)
    }

    @After
    fun limpiarDispatcher() {
        Dispatchers.resetMain()
    }

    @Test
    fun `hidrata perfil y refleja que existe`() = runTest {
        val dao = ProfilePerfilDaoFalso(perfilValido())
        val viewModel = ProfileViewModel(PerfilRepository(dao))

        advanceUntilIdle()

        assertEquals(true, viewModel.hasProfile.value)
        assertEquals("Ana Pérez", viewModel.uiState.value.nombre.valor)
        assertEquals("ana@ejemplo.cl", viewModel.uiState.value.correo.valor)
        assertEquals("foto://perfil", viewModel.uiState.value.fotoUri)
        assertTrue(viewModel.uiState.value.letraGrande)
    }

    @Test
    fun `guardar invalido muestra errores y no persiste`() = runTest {
        val dao = ProfilePerfilDaoFalso(null)
        val viewModel = ProfileViewModel(PerfilRepository(dao))
        advanceUntilIdle()

        viewModel.onGuardar()
        advanceUntilIdle()

        assertEquals("Escribe al menos 2 letras", viewModel.uiState.value.nombre.error)
        assertEquals("Correo no válido", viewModel.uiState.value.correo.error)
        assertFalse(viewModel.uiState.value.esValido)
        assertFalse(viewModel.uiState.value.guardadoExitoso)
        assertTrue(dao.guardados.isEmpty())
    }

    @Test
    fun `guardar perfil valido recorta campos y normaliza telefono`() = runTest {
        val dao = ProfilePerfilDaoFalso(null)
        val viewModel = ProfileViewModel(PerfilRepository(dao))
        advanceUntilIdle()
        completarFormulario(viewModel)
        viewModel.onNombreChange("  Ana Pérez  ")
        viewModel.onCorreoChange(" ana@ejemplo.cl ")
        viewModel.onTelefonoChange("994623089")

        viewModel.onGuardar()
        advanceUntilIdle()

        val guardado = dao.guardados.single()
        assertEquals("Ana Pérez", guardado.nombre)
        assertEquals("ana@ejemplo.cl", guardado.email)
        assertEquals("+56 9 9462 3089", guardado.telefono)
        assertTrue(viewModel.uiState.value.guardadoExitoso)
    }

    @Test
    fun `cambiar foto persiste uri y la refleja en el estado`() = runTest {
        val dao = ProfilePerfilDaoFalso(perfilValido())
        val viewModel = ProfileViewModel(PerfilRepository(dao))
        advanceUntilIdle()

        viewModel.onFotoSeleccionada("foto://nueva")
        advanceUntilIdle()

        assertEquals("foto://nueva", viewModel.uiState.value.fotoUri)
        assertEquals("foto://nueva", dao.actual?.fotoUri)
    }

    @Test
    fun `cambiar letra grande persiste la preferencia aunque el formulario este invalido`() = runTest {
        val existente = perfilValido()
        val dao = ProfilePerfilDaoFalso(existente)
        val viewModel = ProfileViewModel(PerfilRepository(dao))
        advanceUntilIdle()
        viewModel.onNombreChange("")

        viewModel.onLetraGrandeCambiada(true)
        advanceUntilIdle()

        assertTrue(dao.guardados.last().letraGrande)
        assertEquals(existente.email, dao.guardados.last().email)
        assertEquals("", viewModel.uiState.value.nombre.valor)
    }

    @Test
    fun `cambiar foto sin perfil no intenta guardar entidad`() = runTest {
        val dao = ProfilePerfilDaoFalso(null)
        val viewModel = ProfileViewModel(PerfilRepository(dao))
        advanceUntilIdle()

        viewModel.onFotoSeleccionada("foto://temporal")
        advanceUntilIdle()

        assertEquals("foto://temporal", viewModel.uiState.value.fotoUri)
        assertTrue(dao.guardados.isEmpty())
        assertNull(dao.actual)
    }

    private fun completarFormulario(viewModel: ProfileViewModel) {
        viewModel.onNombreChange("Ana Pérez")
        viewModel.onCorreoChange("ana@ejemplo.cl")
        viewModel.onTelefonoChange("+56 9 9462 3089")
        viewModel.onDivisionChange(Division.GELATERIA)
        viewModel.onTamanoChange(TamanoNegocio.SOLO_YO)
        viewModel.onInteresToggle(Interes.RECETAS)
    }

    private fun perfilValido() = PerfilEntity(
        nombre = "Ana Pérez",
        email = "ana@ejemplo.cl",
        telefono = "+56 9 1234 5678",
        division = Division.GELATERIA,
        tamano = TamanoNegocio.SOLO_YO,
        intereses = setOf(Interes.RECETAS),
        fotoUri = "foto://perfil",
        letraGrande = true,
    )
}

private class ProfilePerfilDaoFalso(perfil: PerfilEntity?) : PerfilDao {
    private val estado = MutableStateFlow(perfil)
    val guardados = mutableListOf<PerfilEntity>()
    val actual: PerfilEntity? get() = estado.value

    override fun obtenerPerfil(): Flow<PerfilEntity?> = estado
    override suspend fun obtenerPerfilActual(): PerfilEntity? = estado.value
    override suspend fun guardarPerfil(perfil: PerfilEntity) {
        guardados += perfil
        estado.value = perfil
    }
}
