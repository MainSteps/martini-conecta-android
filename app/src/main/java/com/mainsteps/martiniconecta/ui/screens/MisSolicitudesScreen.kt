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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.TextButton
import com.mainsteps.martiniconecta.model.ProductoInteres
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.contentDescription
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

    val contenido = uiState as? MisSolicitudesUiState.Exito
    contenido?.edicion?.let { solicitud ->
        EdicionSolicitudDialog(contenido, solicitud, viewModel)
    }
    contenido?.paraEliminar?.let { solicitud ->
        AlertDialog(
            onDismissRequest = viewModel::onCancelar,
            title = { Text("¿Eliminar esta solicitud?") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("Se eliminará la solicitud de ${solicitud.nombre} para ${solicitud.producto.etiqueta}.")
                    contenido.mensaje?.let { Text(it, color = MaterialTheme.colorScheme.error) }
                }
            },
            confirmButton = {
                TextButton(onClick = { viewModel.onEliminar(solicitud.id) }, enabled = !contenido.procesando) {
                    Text(if (contenido.procesando) "Eliminando…" else "Eliminar")
                }
            },
            dismissButton = {
                TextButton(onClick = viewModel::onCancelar, enabled = !contenido.procesando) { Text("Cancelar") }
            },
        )
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
                if (estado.mensaje != null && estado.edicion == null && estado.paraEliminar == null) {
                    item { Text(estado.mensaje, style = MaterialTheme.typography.bodyLarge) }
                }
                items(
                    items = estado.solicitudes,
                    key = { solicitud -> solicitud.id },
                ) { solicitud ->
                    SolicitudTarjeta(
                        solicitud = solicitud,
                        enabled = !estado.procesando,
                        onEditar = { viewModel.onEditar(solicitud) },
                        onEliminar = { viewModel.onConfirmarEliminar(solicitud) },
                    )
                }
            }
        }
    }
}

@Composable
private fun SolicitudTarjeta(
    solicitud: SolicitudContacto,
    enabled: Boolean,
    onEditar: () -> Unit,
    onEliminar: () -> Unit,
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
            OutlinedButton(onClick = onEditar, enabled = enabled, modifier = Modifier.heightIn(min = 48.dp)) {
                Icon(Icons.Default.Edit, contentDescription = "Editar solicitud de ${solicitud.nombre}")
                Text("Editar", modifier = Modifier.padding(start = 8.dp))
            }
            OutlinedButton(onClick = onEliminar, enabled = enabled, modifier = Modifier.heightIn(min = 48.dp)) {
                Icon(Icons.Default.Delete, contentDescription = "Eliminar solicitud de ${solicitud.nombre}")
                Text("Eliminar", modifier = Modifier.padding(start = 8.dp))
            }
        }
    }
}

@Composable
private fun EdicionSolicitudDialog(
    estado: MisSolicitudesUiState.Exito,
    solicitud: SolicitudContacto,
    viewModel: MisSolicitudesViewModel,
) {
    var productosAbiertos by remember { mutableStateOf(false) }
    AlertDialog(
        onDismissRequest = viewModel::onCancelar,
        title = { Text("Editar solicitud") },
        text = {
            Column(
                modifier = Modifier.verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                OutlinedTextField(
                    value = solicitud.comuna,
                    onValueChange = { viewModel.onCambiarEdicion(solicitud.copy(comuna = it)) },
                    label = { Text("Comuna") },
                    enabled = !estado.procesando,
                    isError = estado.errorComuna != null,
                    supportingText = { estado.errorComuna?.let { Text("⚠ $it") } },
                    modifier = Modifier.fillMaxWidth(),
                )
                Text("Producto de interés", style = MaterialTheme.typography.bodyLarge)
                Box {
                    OutlinedButton(
                        onClick = { productosAbiertos = true },
                        enabled = !estado.procesando,
                        modifier = Modifier.heightIn(min = 48.dp),
                    ) { Text(solicitud.producto.etiqueta) }
                    DropdownMenu(expanded = productosAbiertos, onDismissRequest = { productosAbiertos = false }) {
                        ProductoInteres.entries.forEach { producto ->
                            DropdownMenuItem(
                                text = { Text(producto.etiqueta, style = MaterialTheme.typography.bodyLarge) },
                                onClick = {
                                    viewModel.onCambiarEdicion(solicitud.copy(producto = producto))
                                    productosAbiertos = false
                                },
                            )
                        }
                    }
                }
                Text(etiquetaEstado(solicitud.estado), style = MaterialTheme.typography.bodyLarge)
                Switch(
                    checked = solicitud.estado == EstadoSolicitud.CONTACTADO,
                    onCheckedChange = { contactado ->
                        viewModel.onCambiarEdicion(solicitud.copy(
                            estado = if (contactado) EstadoSolicitud.CONTACTADO else EstadoSolicitud.PENDIENTE,
                        ))
                    },
                    enabled = !estado.procesando,
                    modifier = Modifier.semantics {
                        contentDescription = "Marcar como contactado"
                    },
                )
                estado.mensaje?.let { Text(it, color = MaterialTheme.colorScheme.error) }
            }
        },
        confirmButton = {
            TextButton(onClick = viewModel::onGuardarEdicion, enabled = !estado.procesando) {
                Text(if (estado.procesando) "Guardando…" else "Guardar")
            }
        },
        dismissButton = {
            TextButton(onClick = viewModel::onCancelar, enabled = !estado.procesando) { Text("Cancelar") }
        },
    )
}

private fun formatearFecha(millis: Long): String {
    val formato = SimpleDateFormat("d MMM yyyy, HH:mm", Locale.forLanguageTag("es-CL"))
    return formato.format(Date(millis))
}

private fun etiquetaEstado(estado: EstadoSolicitud): String = when (estado) {
    EstadoSolicitud.PENDIENTE -> "Estado: Pendiente"
    EstadoSolicitud.CONTACTADO -> "Estado: Contactado"
}
