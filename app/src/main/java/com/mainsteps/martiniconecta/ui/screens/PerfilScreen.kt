package com.mainsteps.martiniconecta.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.activity.ComponentActivity
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mainsteps.martiniconecta.ui.components.PerfilFormularioSelecciones
import com.mainsteps.martiniconecta.ui.components.PerfilFormularioTexto
import com.mainsteps.martiniconecta.ui.components.PerfilMensajeEstilo
import com.mainsteps.martiniconecta.ui.components.PerfilMensajeValidacion
import com.mainsteps.martiniconecta.ui.components.ScreenHeader
import com.mainsteps.martiniconecta.viewmodel.ProfileViewModel

@Composable
fun PerfilScreen(
    profileViewModelFactory: ViewModelProvider.Factory,
    modifier: Modifier = Modifier,
) {
    val activity = LocalContext.current as ComponentActivity
    val viewModel: ProfileViewModel = viewModel(
        viewModelStoreOwner = activity,
        factory = profileViewModelFactory,
    )
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

        PerfilFormularioSelecciones(
            uiState = uiState,
            onDivisionChange = viewModel::onDivisionChange,
            onTamanoChange = viewModel::onTamanoChange,
            onInteresToggle = viewModel::onInteresToggle
        )

        Button(
            onClick = { viewModel.onGuardar() },
            enabled = uiState.esValido,
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 48.dp)
        ) {
            Text(text = "Guardar")
        }

        if (uiState.guardadoExitoso) {
            PerfilMensajeValidacion(
                texto = "Perfil guardado correctamente",
                estilo = PerfilMensajeEstilo.Exito
            )
        }

        if (!uiState.esValido) {
            PerfilMensajeValidacion(
                texto = "Completa los campos que faltan para guardar",
                estilo = PerfilMensajeEstilo.Aviso
            )
        }
    }
}
