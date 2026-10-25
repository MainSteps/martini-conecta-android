package com.mainsteps.martiniconecta.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mainsteps.martiniconecta.ui.components.PerfilFormularioTexto
import com.mainsteps.martiniconecta.ui.components.ScreenHeader
import com.mainsteps.martiniconecta.viewmodel.ProfileViewModel

@Composable
fun PerfilScreen(
    modifier: Modifier = Modifier
) {
    val viewModel: ProfileViewModel = viewModel()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        ScreenHeader(
            title = "Tu perfil",
            subtitle = "Consulta y edita tu información personal."
        )

        PerfilFormularioTexto(
            uiState = uiState,
            onNombreChange = viewModel::onNombreChange,
            onNombreFocusLost = viewModel::onNombreFocusLost,
            onCorreoChange = viewModel::onCorreoChange,
            onCorreoFocusLost = viewModel::onCorreoFocusLost,
            onTelefonoChange = viewModel::onTelefonoChange,
            onTelefonoFocusLost = viewModel::onTelefonoFocusLost
        )
    }
}
