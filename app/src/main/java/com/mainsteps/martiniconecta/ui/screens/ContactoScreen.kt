package com.mainsteps.martiniconecta.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mainsteps.martiniconecta.model.Division
import com.mainsteps.martiniconecta.model.ProductoInteres
import com.mainsteps.martiniconecta.ui.components.OptionButton
import com.mainsteps.martiniconecta.ui.components.PerfilCampoTexto
import com.mainsteps.martiniconecta.ui.components.PerfilMensajeEstilo
import com.mainsteps.martiniconecta.ui.components.PerfilMensajeValidacion
import com.mainsteps.martiniconecta.ui.components.ScreenHeader
import com.mainsteps.martiniconecta.viewmodel.ContactoViewModel

@Composable
fun ContactoScreen(
    contactoViewModelFactory: ViewModelProvider.Factory,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val viewModel: ContactoViewModel = viewModel(factory = contactoViewModelFactory)
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

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
                title = "Quiero que me contacten",
                subtitle = "Déjanos tus datos y guardaremos tu solicitud en este dispositivo.",
            )
        }

        item {
            PerfilCampoTexto(
                label = "Nombre",
                valor = uiState.nombre.valor,
                error = uiState.nombre.error,
                onValorChange = viewModel::onNombreChange,
                onFocusLost = viewModel::onNombreFocusLost,
                keyboardOptions = KeyboardOptions.Default,
                mostrarValidacion = uiState.intentoEnvio,
                modifier = Modifier.fillMaxWidth(),
            )
        }

        item {
            PerfilCampoTexto(
                label = "Teléfono o WhatsApp",
                valor = uiState.telefono.valor,
                error = uiState.telefono.error,
                onValorChange = viewModel::onTelefonoChange,
                onFocusLost = viewModel::onTelefonoFocusLost,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                placeholder = "+56 9 1234 5678",
                mostrarValidacion = uiState.intentoEnvio,
                modifier = Modifier.fillMaxWidth(),
            )
        }

        item {
            SeleccionContacto(
                titulo = "División",
                opciones = Division.entries.toList(),
                etiqueta = Division::etiqueta,
                seleccionada = uiState.division.valor,
                error = uiState.division.error,
                onSeleccionar = viewModel::onDivisionChange,
            )
        }

        item {
            PerfilCampoTexto(
                label = "Comuna",
                valor = uiState.comuna.valor,
                error = uiState.comuna.error,
                onValorChange = viewModel::onComunaChange,
                onFocusLost = viewModel::onComunaFocusLost,
                keyboardOptions = KeyboardOptions.Default,
                mostrarValidacion = uiState.intentoEnvio,
                modifier = Modifier.fillMaxWidth(),
            )
        }

        item {
            SeleccionContacto(
                titulo = "Producto de interés",
                opciones = ProductoInteres.entries.toList(),
                etiqueta = ProductoInteres::etiqueta,
                seleccionada = uiState.producto.valor,
                error = uiState.producto.error,
                onSeleccionar = viewModel::onProductoChange,
            )
        }

        item {
            Button(
                onClick = viewModel::onEnviar,
                enabled = !uiState.enviando,
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 48.dp),
            ) {
                Text(text = if (uiState.enviando) "Guardando…" else "Enviar solicitud")
            }
        }

        if (uiState.enviadoExitoso) {
            item {
                PerfilMensajeValidacion(
                    texto = "Solicitud guardada. Te contactaremos pronto.",
                    estilo = PerfilMensajeEstilo.Exito,
                )
            }
        }

        item {
            Text(
                text = "Prototipo académico: usa datos ficticios.",
                style = MaterialTheme.typography.bodyLarge,
            )
        }
    }
}

@Composable
private fun <T> SeleccionContacto(
    titulo: String,
    opciones: List<T>,
    etiqueta: (T) -> String,
    seleccionada: T?,
    error: String?,
    onSeleccionar: (T) -> Unit,
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(text = titulo, style = MaterialTheme.typography.titleMedium)
        opciones.forEach { opcion ->
            OptionButton(
                label = etiqueta(opcion),
                selected = opcion == seleccionada,
                onClick = { onSeleccionar(opcion) },
            )
        }
        if (error != null) {
            PerfilMensajeValidacion(
                texto = error,
                estilo = PerfilMensajeEstilo.Error,
            )
        } else if (seleccionada != null) {
            PerfilMensajeValidacion(
                texto = "Selección correcta",
                estilo = PerfilMensajeEstilo.Exito,
            )
        }
    }
}
