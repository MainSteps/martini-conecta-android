package com.mainsteps.martiniconecta.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mainsteps.martiniconecta.repository.ContactoRepository
import com.mainsteps.martiniconecta.repository.PerfilRepository

class MisSolicitudesViewModelFactory(
    private val contactoRepository: ContactoRepository,
    private val perfilRepository: PerfilRepository,
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MisSolicitudesViewModel::class.java)) {
            return MisSolicitudesViewModel(contactoRepository, perfilRepository) as T
        }
        throw IllegalArgumentException("ViewModel desconocido: ${modelClass.name}")
    }
}