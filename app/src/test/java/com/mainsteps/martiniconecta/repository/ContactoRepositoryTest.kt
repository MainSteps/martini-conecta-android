package com.mainsteps.martiniconecta.repository

import com.mainsteps.martiniconecta.data.local.SolicitudDao
import com.mainsteps.martiniconecta.data.local.SolicitudEntity
import com.mainsteps.martiniconecta.model.Division
import com.mainsteps.martiniconecta.model.ProductoInteres
import com.mainsteps.martiniconecta.model.SolicitudContacto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class ContactoRepositoryTest {

    @Test
    fun `guardar solicitud conserva todos los datos`() = runBlocking {
        val repository = ContactoRepository(SolicitudDaoFalso())
        val solicitud = SolicitudContacto(
            nombre = "Ana Pérez",
            telefono = "+56 9 1234 5678",
            division = Division.GELATERIA,
            comuna = "Maipú",
            producto = ProductoInteres.CHOCOLATE_ARIBA,
            creadaEn = 1000,
        )

        repository.guardarSolicitud(solicitud)

        assertEquals(solicitud.copy(id = 1), repository.observarSolicitudes().first().single())
    }
}

private class SolicitudDaoFalso : SolicitudDao {
    private val solicitudes = MutableStateFlow<List<SolicitudEntity>>(emptyList())

    override suspend fun guardarSolicitud(solicitud: SolicitudEntity): Long {
        val id = solicitudes.value.size.toLong() + 1
        solicitudes.value = listOf(solicitud.copy(id = id)) + solicitudes.value
        return id
    }

    override fun observarSolicitudes(): Flow<List<SolicitudEntity>> = solicitudes
}
