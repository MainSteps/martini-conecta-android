package com.mainsteps.martiniconecta.repository

import com.mainsteps.martiniconecta.data.remote.SolicitudApi
import com.mainsteps.martiniconecta.model.SolicitudContacto

class ContactoRepository(
    private val api: SolicitudApi,
) {

    suspend fun guardarSolicitud(solicitud: SolicitudContacto): Long {
        return api.crear(solicitud).id
    }

    suspend fun listarSolicitudes(telefono: String? = null): List<SolicitudContacto> {
        return api.listar(telefono)
    }

    suspend fun actualizarSolicitud(solicitud: SolicitudContacto): SolicitudContacto {
        return api.actualizar(solicitud.id, solicitud)
    }

    suspend fun eliminarSolicitud(id: Long) {
        val respuesta = api.eliminar(id)
        if (!respuesta.isSuccessful) throw retrofit2.HttpException(respuesta)
    }
}
