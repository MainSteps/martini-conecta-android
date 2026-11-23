package com.mainsteps.martiniconecta.repository

import com.mainsteps.martiniconecta.data.remote.SolicitudApi
import com.mainsteps.martiniconecta.model.Division
import com.mainsteps.martiniconecta.model.ProductoInteres
import com.mainsteps.martiniconecta.model.SolicitudContacto
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import retrofit2.Response
import retrofit2.HttpException
import okhttp3.ResponseBody.Companion.toResponseBody

class ContactoRepositoryTest {

    @Test
    fun `guardar solicitud conserva todos los datos`() = runBlocking {
        val api = SolicitudApiFalsa()
        val repository = ContactoRepository(api)
        val id = repository.guardarSolicitud(solicitudEjemplo)

        assertEquals(1, id)
        assertEquals(solicitudEjemplo.copy(id = 1), repository.listarSolicitudes().single())
    }

    @Test
    fun `listar solicitudes devuelve vacio sin datos`() = runBlocking {
        val repository = ContactoRepository(SolicitudApiFalsa())

        assertTrue(repository.listarSolicitudes().isEmpty())
    }

    @Test
    fun `listar solicitudes envia telefono a la api`() = runBlocking {
        val api = SolicitudApiFalsa()
        val repository = ContactoRepository(api)
        api.crear(solicitudEjemplo)
        api.crear(
            solicitudEjemplo.copy(
                nombre = "Otra",
                telefono = "+56 9 9999 8888",
            ),
        )

        val resultado = repository.listarSolicitudes("+56 9 1234 5678")

        assertEquals("+56 9 1234 5678", api.ultimoTelefonoListado)
        assertEquals(1, resultado.size)
        assertEquals("+56 9 1234 5678", resultado.single().telefono)
    }

    @Test
    fun `actualizar envia el id correcto y conserva los cambios`() = runBlocking {
        val api = SolicitudApiFalsa()
        val repository = ContactoRepository(api)
        val id = repository.guardarSolicitud(solicitudEjemplo)
        val edicion = solicitudEjemplo.copy(id = id, comuna = "Ñuñoa")

        assertEquals(edicion, repository.actualizarSolicitud(edicion))
        assertEquals(id, api.ultimoIdActualizado)
        assertEquals(edicion, api.obtener(id))
    }

    @Test
    fun `eliminar envia el id y elimina la solicitud`() = runBlocking {
        val api = SolicitudApiFalsa()
        val repository = ContactoRepository(api)
        val id = repository.guardarSolicitud(solicitudEjemplo)

        repository.eliminarSolicitud(id)

        assertEquals(id, api.ultimoIdEliminado)
        assertTrue(repository.listarSolicitudes().isEmpty())
    }

    @Test
    fun `delete no exitoso lanza excepcion y conserva datos`() = runBlocking {
        val api = SolicitudApiFalsa()
        val repository = ContactoRepository(api)
        val id = repository.guardarSolicitud(solicitudEjemplo)
        api.errorHttpEliminar = true

        val error = runCatching { repository.eliminarSolicitud(id) }.exceptionOrNull()

        assertTrue(error is HttpException)
        assertEquals(500, (error as HttpException).code())
        assertEquals(1, repository.listarSolicitudes().size)
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

class SolicitudApiFalsa(
    var fallarAlCrear: Boolean = false,
    var fallarAlListar: Boolean = false,
) : SolicitudApi {

    private val solicitudes = mutableListOf<SolicitudContacto>()

    var ultimoTelefonoListado: String? = null
    var ultimoIdActualizado: Long? = null
    var ultimoIdEliminado: Long? = null
    var fallarAlActualizar = false
    var fallarAlEliminar = false
    var errorHttpEliminar = false
    var vecesListado = 0

    override suspend fun listar(telefono: String?): List<SolicitudContacto> {
        vecesListado++
        if (fallarAlListar) {
            throw RuntimeException("API")
        }
        ultimoTelefonoListado = telefono
        val lista = solicitudes.toList()
        return if (telefono.isNullOrBlank()) {
            lista
        } else {
            lista.filter { solicitud -> solicitud.telefono == telefono }
        }
    }

    override suspend fun obtener(id: Long): SolicitudContacto {
        return solicitudes.first { it.id == id }
    }

    override suspend fun crear(body: SolicitudContacto): SolicitudContacto {
        if (fallarAlCrear) {
            throw RuntimeException("API")
        }
        val id = solicitudes.size.toLong() + 1
        val guardada = body.copy(id = id)
        solicitudes.add(guardada)
        return guardada
    }

    override suspend fun actualizar(id: Long, body: SolicitudContacto): SolicitudContacto {
        ultimoIdActualizado = id
        if (fallarAlActualizar) throw RuntimeException("API")
        val indice = solicitudes.indexOfFirst { it.id == id }
        val actualizada = body.copy(id = id)
        solicitudes[indice] = actualizada
        return actualizada
    }

    override suspend fun eliminar(id: Long): Response<Unit> {
        ultimoIdEliminado = id
        if (fallarAlEliminar) throw RuntimeException("API")
        if (errorHttpEliminar) return Response.error(500, "Error".toResponseBody())
        solicitudes.removeAll { it.id == id }
        return Response.success(Unit)
    }
}
