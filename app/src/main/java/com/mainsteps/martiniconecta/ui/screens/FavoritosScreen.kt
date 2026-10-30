package com.mainsteps.martiniconecta.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mainsteps.martiniconecta.model.Contenido
import com.mainsteps.martiniconecta.ui.components.ContentCard
import com.mainsteps.martiniconecta.ui.components.EmptyState
import com.mainsteps.martiniconecta.ui.components.PerfilMensajeEstilo
import com.mainsteps.martiniconecta.ui.components.PerfilMensajeValidacion
import com.mainsteps.martiniconecta.ui.components.ScreenHeader

@Composable
fun FavoritosScreen(
    favoritos: List<Contenido>,
    mensajeError: String?,
    onOpenDetail: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(vertical = 24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        item {
            ScreenHeader(
                title = "Favoritos",
                subtitle = "Tus recetas guardadas están disponibles incluso sin conexión.",
                modifier = Modifier.padding(horizontal = 24.dp),
            )
        }

        if (mensajeError != null) {
            item {
                PerfilMensajeValidacion(
                    texto = mensajeError,
                    estilo = PerfilMensajeEstilo.Error,
                    modifier = Modifier.padding(horizontal = 24.dp),
                )
            }
        }

        if (favoritos.isEmpty()) {
            item {
                EmptyState(
                    title = "Todavía no tienes favoritos",
                    message = "Abre una receta y toca Agregar a favoritos para verla aquí.",
                    modifier = Modifier.padding(horizontal = 24.dp),
                )
            }
        } else {
            items(
                items = favoritos,
                key = Contenido::id,
            ) { receta ->
                ContentCard(
                    title = receta.titulo,
                    description = receta.descripcion,
                    supportingText = receta.division.etiqueta,
                    onClick = { onOpenDetail(receta.id) },
                    modifier = Modifier.padding(horizontal = 24.dp),
                )
            }
        }
    }
}
