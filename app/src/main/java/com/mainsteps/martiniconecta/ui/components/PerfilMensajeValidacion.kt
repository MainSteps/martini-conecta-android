package com.mainsteps.martiniconecta.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.LiveRegionMode
import androidx.compose.ui.semantics.liveRegion
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp

enum class PerfilMensajeEstilo {
    Error,
    Exito,
    Aviso
}

@Composable
fun PerfilMensajeValidacion(
    texto: String,
    estilo: PerfilMensajeEstilo,
    modifier: Modifier = Modifier
) {
    val color = when (estilo) {
        PerfilMensajeEstilo.Error -> MaterialTheme.colorScheme.error
        PerfilMensajeEstilo.Exito -> MaterialTheme.colorScheme.primary
        PerfilMensajeEstilo.Aviso -> MaterialTheme.colorScheme.onSurfaceVariant
    }
    val icono: ImageVector = when (estilo) {
        PerfilMensajeEstilo.Error -> Icons.Filled.Warning
        PerfilMensajeEstilo.Exito -> Icons.Filled.Check
        PerfilMensajeEstilo.Aviso -> Icons.Filled.Warning
    }

    val modifierMensaje = if (estilo == PerfilMensajeEstilo.Exito) {
        modifier.semantics { liveRegion = LiveRegionMode.Polite }
    } else {
        modifier
    }

    Row(
        modifier = modifierMensaje,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Icon(
            imageVector = icono,
            contentDescription = null,
            tint = color,
            modifier = Modifier.size(20.dp)
        )
        Text(
            text = texto,
            style = MaterialTheme.typography.bodySmall,
            color = color
        )
    }
}
