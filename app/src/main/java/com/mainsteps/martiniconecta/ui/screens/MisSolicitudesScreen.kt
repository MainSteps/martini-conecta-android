package com.mainsteps.martiniconecta.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mainsteps.martiniconecta.model.EstadoSolicitud
import com.mainsteps.martiniconecta.model.SolicitudContacto
import com.mainsteps.martiniconecta.ui.components.EmptyState
import com.mainsteps.martiniconecta.ui.components.ScreenHeader
import com.mainsteps.martiniconecta.viewmodel.MisSolicitudesUiState
import com.mainsteps.martiniconecta.viewmodel.MisSolicitudesViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun MisSolicitudesScreen(
    misSolicitudesViewModelFactory: ViewModelProvider.Factory,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val viewModel: MisSolicitudesViewModel = viewModel(factory = misSolicitudesViewModelFactory)
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.cargar()
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp),
    ) {
        item {
            OutlinedButton(
                onClick = onBack,
                modifier = Modifier
                    .padding(top = 16.dp)
                    .heightIn(min = 48.dp),
            ) {
                Text(text = "Volver")
            }
        }

        item {
            ScreenHeader(
                title = "Mis solicitudes",
                subtitle = "Solicitudes enviadas con tu número.",
            )
        }

        when (val estado = uiState) {
            MisSolicitudesUiState.Cargando -> {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 48.dp),
                        contentAlignment = Alignment.Center,
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }

            MisSolicitudesUiState.Vacio -> {
                item {
                    EmptyState(
                        title = "Aún no hay solicitudes",
                        message = "Cuando envíes el formulario de contacto, aparecerán aquí.",
                        modifier = Modifier.fillMaxWidth(),
                    )
                }
            }

            is MisSolicitudesUiState.Error -> {
                item {
                    EmptyState(
                        title = "No se pudo cargar",
                        message = estado.mensaje,
                        actionLabel = "Reintentar",
                        onAction = viewModel::reintentar,
                        modifier = Modifier.fillMaxWidth(),
                    )
                }
            }

            is MisSolicitudesUiState.Exito -> {
                items(
                    items = estado.solicitudes,
                    key = { solicitud -> solicitud.id },
                ) { solicitud ->
                    SolicitudTarjeta(solicitud = solicitud)
                }
            }
        }
    }
}

@Composable
private fun SolicitudTarjeta(
    solicitud: SolicitudContacto,
    modifier: Modifier = Modifier,
) {
    ElevatedCard(
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 48.dp),
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Text(
                text = solicitud.nombre,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.semantics { heading() },
            )
            Text(
                text = solicitud.producto.etiqueta,
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary,
            )
            Text(
                text = "${solicitud.division.etiqueta} · ${solicitud.comuna}",
                style = MaterialTheme.typography.bodyLarge,
            )
            Text(
                text = formatearFecha(solicitud.creadaEn),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            Text(
                text = etiquetaEstado(solicitud.estado),
                style = MaterialTheme.typography.bodyLarge,
            )
        }
    }
}

private fun formatearFecha(millis: Long): String {
    val formato = SimpleDateFormat("d MMM yyyy, HH:mm", Locale.forLanguageTag("es-CL"))
    return formato.format(Date(millis))
}

private fun etiquetaEstado(estado: EstadoSolicitud): String = when (estado) {
    EstadoSolicitud.PENDIENTE -> "Estado: Pendiente"
    EstadoSolicitud.CONTACTADO -> "Estado: Contactado"
}