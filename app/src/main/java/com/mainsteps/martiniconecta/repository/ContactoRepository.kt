package com.mainsteps.martiniconecta.repository

import com.mainsteps.martiniconecta.data.local.SolicitudDao
import com.mainsteps.martiniconecta.data.local.SolicitudEntity
import com.mainsteps.martiniconecta.model.SolicitudContacto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ContactoRepository(
    private val solicitudDao: SolicitudDao,
) {

    suspend fun guardarSolicitud(solicitud: SolicitudContacto): Long {
        return solicitudDao.guardarSolicitud(solicitud.toEntity())
    }

    fun observarSolicitudes(): Flow<List<SolicitudContacto>> {
        return solicitudDao.observarSolicitudes().map { solicitudes ->
            solicitudes.map { solicitud -> solicitud.toModel() }
        }
    }

    private fun SolicitudContacto.toEntity(): SolicitudEntity {
        return SolicitudEntity(
            id = id,
            nombre = nombre,
            telefono = telefono,
            division = division,
            comuna = comuna,
            producto = producto,
            creadaEn = creadaEn,
        )
    }

    private fun SolicitudEntity.toModel(): SolicitudContacto {
        return SolicitudContacto(
            id = id,
            nombre = nombre,
            telefono = telefono,
            division = division,
            comuna = comuna,
            producto = producto,
            creadaEn = creadaEn,
        )
    }
}
