package com.mainsteps.martiniconecta.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
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
import com.mainsteps.martiniconecta.model.Contenido
import com.mainsteps.martiniconecta.ui.components.ContentCard
import com.mainsteps.martiniconecta.ui.components.EmptyState
import com.mainsteps.martiniconecta.ui.components.PerfilMensajeEstilo
import com.mainsteps.martiniconecta.ui.components.PerfilMensajeValidacion
import com.mainsteps.martiniconecta.ui.components.ScreenHeader
import com.mainsteps.martiniconecta.viewmodel.InicioUiState
import com.mainsteps.martiniconecta.viewmodel.InicioViewModel
import kotlinx.coroutines.delay

@Composable
fun InicioScreen(
    onOpenDetail: (String) -> Unit,
    inicioViewModelFactory: ViewModelProvider.Factory,
    perfilGuardadoConfirmado: Boolean = false,
    onConfirmacionPerfilLeida: () -> Unit = {},
    modifier: Modifier = Modifier,
    inicioViewModel: InicioViewModel = viewModel(factory = inicioViewModelFactory),
) {
    val uiState by inicioViewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(perfilGuardadoConfirmado) {
        if (perfilGuardadoConfirmado) {
            delay(5_000)
            onConfirmacionPerfilLeida()
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(vertical = 24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        if (perfilGuardadoConfirmado) {
            PerfilMensajeValidacion(
                texto = "Perfil guardado correctamente",
                estilo = PerfilMensajeEstilo.Exito,
                modifier = Modifier.padding(horizontal = 24.dp),
            )
        }

        when (val estado = uiState) {
            InicioUiState.Cargando -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    CircularProgressIndicator()
                }
            }

            InicioUiState.SinPerfil -> {
                EmptyState(
                    title = "Aún no tienes perfil",
                    message = "Completa tu perfil para ver recetas pensadas para tu rubro.",
                    modifier = Modifier.padding(horizontal = 24.dp),
                )
            }

            is InicioUiState.ConRecetas -> {
                InicioConRecetas(
                    nombre = estado.nombre,
                    titulo = estado.titulo,
                    recetas = estado.recetas,
                    onOpenDetail = onOpenDetail,
                )
            }

            is InicioUiState.Vacio -> {
                InicioVacio(
                    nombre = estado.nombre,
                    titulo = estado.titulo,
                )
            }
        }
    }
}

@Composable
private fun InicioConRecetas(
    nombre: String,
    titulo: String,
    recetas: List<Contenido>,
    onOpenDetail: (String) -> Unit,
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        item {
            ScreenHeader(
                title = "Hola, $nombre",
                modifier = Modifier.padding(horizontal = 24.dp),
            )
        }

        item {
            Text(
                text = titulo,
                modifier = Modifier
                    .padding(horizontal = 24.dp)
                    .semantics { heading() },
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface,
            )
        }

        items(
            items = recetas,
            key = { receta -> receta.id },
        ) { receta ->
            ContentCard(
                title = receta.titulo,
                description = receta.descripcion,
                supportingText = receta.categoria,
                onClick = { onOpenDetail(receta.id) },
                modifier = Modifier.padding(horizontal = 24.dp),
            )
        }
    }
}

@Composable
private fun InicioVacio(
    nombre: String,
    titulo: String,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        ScreenHeader(title = "Hola, $nombre")

        Text(
            text = titulo,
            modifier = Modifier.semantics { heading() },
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSurface,
        )

        EmptyState(
            title = "No hay recetas para tu rubro",
            message = "Puedes cambiar tu división en Perfil o revisar todas las recetas en Explorar.",
        )
    }
}
