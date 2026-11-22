package com.mainsteps.martiniconecta.viewmodel

import com.mainsteps.martiniconecta.data.local.PerfilDao
import com.mainsteps.martiniconecta.data.local.PerfilEntity
import com.mainsteps.martiniconecta.model.Contenido
import com.mainsteps.martiniconecta.model.Dificultad
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
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class InicioViewModelTest {

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
    fun `sin perfil muestra estado sin perfil`() = runTest {
        val viewModel = crearViewModel(perfil = null, catalogo = emptyList())

        advanceUntilIdle()

        assertEquals(InicioUiState.SinPerfil, viewModel.uiState.value)
    }

    @Test
    fun `filtra recetas por division y presenta el titulo correspondiente`() = runTest {
        val recetas = listOf(
            receta("pan", Division.PANADERIA_PASTELERIA),
            receta("gelato", Division.GELATERIA),
            receta("hotel", Division.FOOD_SERVICE),
            receta("tienda", Division.RETAIL),
        )
        val casos = listOf(
            Division.PANADERIA_PASTELERIA to "Para tu panadería",
            Division.GELATERIA to "Para tu heladería",
            Division.FOOD_SERVICE to "Para tu servicio gastronómico",
            Division.RETAIL to "Para tu negocio retail",
        )

        casos.forEach { (division, titulo) ->
            val viewModel = crearViewModel(perfil(division), recetas)
            advanceUntilIdle()

            val estado = viewModel.uiState.value as InicioUiState.ConRecetas
            assertEquals("Ana", estado.nombre)
            assertEquals(titulo, estado.titulo)
            assertTrue(estado.recetas.all { it.division == division })
        }
    }

    @Test
    fun `sin recetas para la division muestra estado vacio`() = runTest {
        val viewModel = crearViewModel(
            perfil = perfil(Division.RETAIL),
            catalogo = listOf(receta("gelato", Division.GELATERIA)),
        )

        advanceUntilIdle()

        assertEquals(
            InicioUiState.Vacio("Ana", "Para tu negocio retail"),
            viewModel.uiState.value,
        )
    }

    private fun crearViewModel(
        perfil: PerfilEntity?,
        catalogo: List<Contenido>,
    ) = InicioViewModel(PerfilRepository(InicioPerfilDaoFalso(perfil))) { catalogo }

    private fun perfil(division: Division) = PerfilEntity(
        nombre = "Ana",
        email = "ana@ejemplo.cl",
        telefono = null,
        division = division,
        tamano = TamanoNegocio.SOLO_YO,
        intereses = setOf(Interes.RECETAS),
        fotoUri = null,
    )

    private fun receta(id: String, division: Division) = Contenido(
        id = id,
        titulo = id,
        descripcion = "Receta de prueba",
        division = division,
        categoria = "Postre",
        dificultad = Dificultad.BASICO,
        productosMartini = emptyList(),
        rindePorciones = 1,
        ingredientes = emptyList(),
        pasos = emptyList(),
        imagenResId = 0,
        esVegano = false,
    )
}

private class InicioPerfilDaoFalso(private val perfil: PerfilEntity?) : PerfilDao {
    override fun obtenerPerfil(): Flow<PerfilEntity?> = flowOf(perfil)
    override suspend fun obtenerPerfilActual(): PerfilEntity? = perfil
    override suspend fun guardarPerfil(perfil: PerfilEntity) = Unit
}
