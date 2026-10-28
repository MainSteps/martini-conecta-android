package com.mainsteps.martiniconecta

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mainsteps.martiniconecta.data.local.AppDatabase
import com.mainsteps.martiniconecta.navigation.AppNavigation
import com.mainsteps.martiniconecta.repository.PerfilRepository
import com.mainsteps.martiniconecta.repository.ContactoRepository
import com.mainsteps.martiniconecta.repository.FavoritosRepository
import com.mainsteps.martiniconecta.ui.theme.MartiniConecta_Grupo15Theme
import com.mainsteps.martiniconecta.viewmodel.ProfileViewModel
import com.mainsteps.martiniconecta.viewmodel.ProfileViewModelFactory
import com.mainsteps.martiniconecta.viewmodel.FavoritosViewModelFactory
import com.mainsteps.martiniconecta.viewmodel.ContactoViewModelFactory

class MainActivity : ComponentActivity() {

    private val perfilRepository by lazy {
        PerfilRepository(AppDatabase.getInstance(applicationContext).perfilDao())
    }

    private val profileViewModelFactory by lazy {
        ProfileViewModelFactory(perfilRepository)
    }

    private val favoritosViewModelFactory by lazy {
        FavoritosViewModelFactory(
            FavoritosRepository(AppDatabase.getInstance(applicationContext).favoritoDao())
        )
    }

    private val contactoViewModelFactory by lazy {
        ContactoViewModelFactory(
            ContactoRepository(AppDatabase.getInstance(applicationContext).solicitudDao()),
            perfilRepository,
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            val profileViewModel: ProfileViewModel = viewModel(
                factory = profileViewModelFactory
            )
            val uiState by profileViewModel.uiState.collectAsStateWithLifecycle()
            val hasProfile by profileViewModel.hasProfile.collectAsStateWithLifecycle()

            MartiniConecta_Grupo15Theme(letraGrande = uiState.letraGrande) {
                when (val tienePerfil = hasProfile) {
                    null -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }

                    else -> {
                        AppNavigation(
                            hasProfile = tienePerfil,
                            profileViewModelFactory = profileViewModelFactory,
                            favoritosViewModelFactory = favoritosViewModelFactory,
                            contactoViewModelFactory = contactoViewModelFactory,
                        )
                    }
                }
            }
        }
    }
}
