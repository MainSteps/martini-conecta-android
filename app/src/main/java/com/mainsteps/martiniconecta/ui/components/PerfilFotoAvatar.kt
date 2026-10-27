package com.mainsteps.martiniconecta.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

private val TamanoFotoPerfil = 120.dp

@Composable
fun PerfilFotoAvatar(
    fotoUri: String?,
    modifier: Modifier = Modifier,
) {
    val forma = CircleShape
    val modifierCircular = modifier
        .size(TamanoFotoPerfil)
        .clip(forma)

    if (fotoUri != null) {
        AsyncImage(
            model = fotoUri,
            contentDescription = "Foto de perfil",
            modifier = modifierCircular,
            contentScale = ContentScale.Crop,
        )
    } else {
        Box(
            modifier = modifierCircular
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .semantics { contentDescription = "Sin foto de perfil" },
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                imageVector = Icons.Filled.Person,
                contentDescription = null,
                modifier = Modifier.size(48.dp),
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}
