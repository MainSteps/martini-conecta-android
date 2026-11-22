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

class ContactoRepositoryTest {

    @Test
    fun `guardar solicitud conserva todos los datos`() = runBlocking {
        val api = SolicitudApiFalsa()
        val repository = ContactoRepository(api)
        val solicitud = SolicitudContacto(
            nombre = "Ana Pérez",
            telefono = "+56 9 1234 5678",
            division = Division.GELATERIA,
            comuna = "Maipú",
            producto = ProductoInteres.CHOCOLATE_ARIBA,
            creadaEn = 1000,
        )

        val id = repository.guardarSolicitud(solicitud)

        assertEquals(1, id)
        assertEquals(solicitud.copy(id = 1), repository.listarSolicitudes().single())
    }

    @Test
    fun `listar solicitudes devuelve vacio sin datos`() = runBlocking {
        val repository = ContactoRepository(SolicitudApiFalsa())

        assertTrue(repository.listarSolicitudes().isEmpty())
    }
}

class SolicitudApiFalsa(
    var fallarAlCrear: Boolean = false,
    var fallarAlListar: Boolean = false,
) : SolicitudApi {

    private val solicitudes = mutableListOf<SolicitudContacto>()

    override suspend fun listar(): List<SolicitudContacto> {
        if (fallarAlListar) {
            throw RuntimeException("API")
        }
        return solicitudes.toList()
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
        val indice = solicitudes.indexOfFirst { it.id == id }
        val actualizada = body.copy(id = id)
        solicitudes[indice] = actualizada
        return actualizada
    }

    override suspend fun eliminar(id: Long): Response<Unit> {
        solicitudes.removeAll { it.id == id }
        return Response.success(Unit)
    }
}