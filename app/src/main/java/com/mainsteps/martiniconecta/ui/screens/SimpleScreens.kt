package com.mainsteps.martiniconecta.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import com.mainsteps.martiniconecta.ui.components.PerfilMensajeEstilo
import com.mainsteps.martiniconecta.ui.components.PerfilMensajeValidacion

@Composable
fun InicioScreen(
    perfilGuardadoConfirmado: Boolean = false,
    onConfirmacionPerfilLeida: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    LaunchedEffect(perfilGuardadoConfirmado) {
        if (perfilGuardadoConfirmado) {
            delay(5_000)
            onConfirmacionPerfilLeida()
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        if (perfilGuardadoConfirmado) {
            PerfilMensajeValidacion(
                texto = "Perfil guardado correctamente",
                estilo = PerfilMensajeEstilo.Exito
            )
        }

        ScreenPlaceholder(
            title = "Inicio",
            description = "Aquí aparecerá la actividad principal de Martini Conecta.",
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
fun FavoritosScreen(
    modifier: Modifier = Modifier
) {
    ScreenPlaceholder(
        title = "Favoritos",
        description = "Aquí encontrarás el contenido que hayas guardado.",
        modifier = modifier
    )
}
