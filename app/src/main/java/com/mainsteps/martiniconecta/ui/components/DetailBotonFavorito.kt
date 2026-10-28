package com.mainsteps.martiniconecta.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp

private const val DuracionColorFavoritoMs = 300
private const val DuracionPulsoSubidaMs = 150
private const val DuracionPulsoBajadaMs = 150
private const val EscalaPulsoMaxima = 1.2f

@Composable
fun DetailBotonFavorito(
    isFavorite: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val escalaIcono = remember { Animatable(1f) }
    var isFavoritePrevio by remember { mutableStateOf(isFavorite) }

    LaunchedEffect(isFavorite) {
        if (isFavoritePrevio != isFavorite) {
            escalaIcono.animateTo(EscalaPulsoMaxima, tween(DuracionPulsoSubidaMs))
            escalaIcono.animateTo(1f, tween(DuracionPulsoBajadaMs))
            isFavoritePrevio = isFavorite
        }
    }

    val colorIcono by animateColorAsState(
        targetValue = if (isFavorite) {
            MaterialTheme.colorScheme.primary
        } else {
            MaterialTheme.colorScheme.onSurfaceVariant
        },
        animationSpec = tween(DuracionColorFavoritoMs),
        label = "ColorIconoFavorito",
    )

    OutlinedButton(
        onClick = onClick,
        modifier = modifier.heightIn(min = 48.dp),
    ) {
        Icon(
            imageVector = if (isFavorite) {
                Icons.Default.Favorite
            } else {
                Icons.Default.FavoriteBorder
            },
            contentDescription = null,
            tint = colorIcono,
            modifier = Modifier.graphicsLayer {
                scaleX = escalaIcono.value
                scaleY = escalaIcono.value
            },
        )
        Text(
            text = if (isFavorite) {
                "Quitar de favoritos"
            } else {
                "Agregar a favoritos"
            },
            modifier = Modifier.padding(start = 8.dp),
        )
    }
}
