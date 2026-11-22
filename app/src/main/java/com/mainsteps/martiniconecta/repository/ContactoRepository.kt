package com.mainsteps.martiniconecta.repository

import com.mainsteps.martiniconecta.data.remote.SolicitudApi
import com.mainsteps.martiniconecta.model.SolicitudContacto

class ContactoRepository(
    private val api: SolicitudApi,
) {

    suspend fun guardarSolicitud(solicitud: SolicitudContacto): Long {
        return api.crear(solicitud).id
    }

    suspend fun listarSolicitudes(): List<SolicitudContacto> {
        return api.listar()
    }
}