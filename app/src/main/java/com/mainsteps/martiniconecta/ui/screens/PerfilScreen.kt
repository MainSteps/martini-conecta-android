package com.mainsteps.martiniconecta.ui.screens

import android.Manifest
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import androidx.activity.compose.LocalActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.core.content.ContextCompat
import com.mainsteps.martiniconecta.ui.components.PerfilFormularioSelecciones
import com.mainsteps.martiniconecta.ui.components.PerfilFormularioTexto
import com.mainsteps.martiniconecta.ui.components.PerfilFotoAvatar
import com.mainsteps.martiniconecta.ui.components.PerfilMensajeEstilo
import com.mainsteps.martiniconecta.ui.components.PerfilMensajeValidacion
import com.mainsteps.martiniconecta.ui.components.ScreenHeader
import com.mainsteps.martiniconecta.util.FotoHelper
import com.mainsteps.martiniconecta.viewmodel.ProfileViewModel

@Composable
fun PerfilScreen(
    profileViewModelFactory: ViewModelProvider.Factory,
    onOpenContacto: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val viewModelStoreOwner = LocalActivity.current as? ViewModelStoreOwner ?: return
    val context = LocalContext.current
    val viewModel: ProfileViewModel = viewModel(
        viewModelStoreOwner = viewModelStoreOwner,
        factory = profileViewModelFactory,
    )
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var fotoCamaraPendiente by rememberSaveable { mutableStateOf<String?>(null) }
    var mensajeCamara by rememberSaveable { mutableStateOf<String?>(null) }

    val galeriaLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
    ) { uri ->
        if (uri == null) {
            return@rememberLauncherForActivityResult
        }
        try {
            context.contentResolver.takePersistableUriPermission(
                uri,
                Intent.FLAG_GRANT_READ_URI_PERMISSION,
            )
        } catch (_: SecurityException) {
        }
        viewModel.onFotoSeleccionada(uri.toString())
    }

    val camaraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
    ) { fotoTomada ->
        val uri = fotoCamaraPendiente
        fotoCamaraPendiente = null
        if (fotoTomada && uri != null) {
            viewModel.onFotoSeleccionada(uri)
            mensajeCamara = null
        }
    }

    val abrirCamara = remember(context, camaraLauncher) {
        {
            val uri = FotoHelper.crearUri(context)
            fotoCamaraPendiente = uri.toString()
            mensajeCamara = null
            try {
                camaraLauncher.launch(uri)
            } catch (_: ActivityNotFoundException) {
                fotoCamaraPendiente = null
                mensajeCamara = "No encontramos una aplicación de cámara. Puedes elegir una foto de la galería."
            }
        }
    }

    val permisoCamaraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
    ) { concedido ->
        if (concedido) {
            abrirCamara()
        } else {
            mensajeCamara = "La cámara necesita permiso. Puedes seguir sin foto o elegir una de la galería."
        }
    }

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

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            PerfilFotoAvatar(fotoUri = uiState.fotoUri)
            Button(
                onClick = {
                    galeriaLauncher.launch(
                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly),
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 48.dp),
            ) {
                Text(text = "Elegir de galería")
            }

            OutlinedButton(
                onClick = {
                    if (
                        ContextCompat.checkSelfPermission(
                            context,
                            Manifest.permission.CAMERA,
                        ) == PackageManager.PERMISSION_GRANTED
                    ) {
                        abrirCamara()
                    } else {
                        permisoCamaraLauncher.launch(Manifest.permission.CAMERA)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 48.dp),
            ) {
                Text(text = "Tomar foto")
            }

            if (mensajeCamara != null) {
                PerfilMensajeValidacion(
                    texto = mensajeCamara.orEmpty(),
                    estilo = PerfilMensajeEstilo.Aviso,
                )
            }
        }

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

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 48.dp)
                .toggleable(
                    value = uiState.letraGrande,
                    onValueChange = viewModel::onLetraGrandeCambiada,
                    role = Role.Switch,
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = "Letra grande",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.weight(1f),
            )
            Switch(
                checked = uiState.letraGrande,
                onCheckedChange = null,
                modifier = Modifier.clearAndSetSemantics { },
            )
        }

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

        uiState.mensajeError?.let { mensaje ->
            PerfilMensajeValidacion(
                texto = mensaje,
                estilo = PerfilMensajeEstilo.Error,
            )
        }

        if (!uiState.esValido) {
            PerfilMensajeValidacion(
                texto = "Completa los campos que faltan para guardar",
                estilo = PerfilMensajeEstilo.Aviso
            )
        }

        OutlinedButton(
            onClick = onOpenContacto,
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 48.dp),
        ) {
            Text(text = "Quiero que me contacten")
        }
    }
}
