package com.mainsteps.martiniconecta.repository

import com.mainsteps.martiniconecta.data.local.PerfilDao
import com.mainsteps.martiniconecta.data.local.PerfilEntity
import kotlinx.coroutines.flow.Flow

class PerfilRepository(
    private val perfilDao: PerfilDao,
) {

    fun obtenerPerfil(): Flow<PerfilEntity?> = perfilDao.obtenerPerfil()

    suspend fun guardarPerfil(perfil: PerfilEntity) {
        perfilDao.guardarPerfil(perfil)
    }
}
