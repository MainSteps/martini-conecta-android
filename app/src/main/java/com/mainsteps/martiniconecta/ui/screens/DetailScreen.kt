package com.mainsteps.martiniconecta.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.Image
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mainsteps.martiniconecta.model.Ingrediente
import com.mainsteps.martiniconecta.model.Contenido
import com.mainsteps.martiniconecta.ui.components.DetailBotonFavorito
import com.mainsteps.martiniconecta.ui.components.EmptyState
import com.mainsteps.martiniconecta.viewmodel.DetailViewModel

@Composable
fun DetailScreen(
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    isFavorite: Boolean = false,
    onFavorite: (Contenido) -> Unit = {},
    detailViewModel: DetailViewModel = viewModel()
) {
    val uiState by detailViewModel.uiState.collectAsStateWithLifecycle()
    val receta = uiState.receta

    if (receta == null) {
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                IconButton(onClick = onBack) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Volver a explorar"
                    )
                }
            }
            item {
                EmptyState(
                    title = "Receta no disponible",
                    message = "No pudimos encontrar esta receta en el catálogo.",
                    actionLabel = "Volver a explorar",
                    onAction = onBack
                )
            }
        }
        return
    }

    var multiplicador by rememberSaveable(receta.id) {
        mutableFloatStateOf(1f)
    }
    val ingredientesEscalados by remember(receta) {
        derivedStateOf {
            receta.ingredientes.map { ingrediente ->
                ingrediente.copy(
                    cantidad = ingrediente.cantidad * multiplicador
                )
            }
        }
    }

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            IconButton(
                onClick = onBack,
                modifier = Modifier.padding(start = 12.dp, top = 8.dp)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Volver a explorar"
                )
            }
        }

        item {
            Image(
                painter = painterResource(receta.imagenResId),
                contentDescription = "Imagen de ${receta.titulo}",
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 220.dp),
                contentScale = ContentScale.Crop
            )
        }

        item {
            Text(
                text = receta.division.etiqueta,
                modifier = Modifier.padding(horizontal = 24.dp),
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary
            )
        }

        item {
            Text(
                text = receta.titulo,
                modifier = Modifier.padding(horizontal = 24.dp),
                style = MaterialTheme.typography.headlineMedium
            )
        }

        item {
            Text(
                text = receta.descripcion,
                modifier = Modifier.padding(horizontal = 24.dp),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        item {
            Text(
                text = "Dificultad: ${receta.dificultad.etiqueta} · Rinde: ${receta.rindePorciones} porciones",
                modifier = Modifier.padding(horizontal = 24.dp),
                style = MaterialTheme.typography.titleSmall
            )
        }

        item {
            DetailBotonFavorito(
                isFavorite = isFavorite,
                onClick = { onFavorite(receta) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
            )
        }

        item {
            DetailSectionTitle(text = "Productos Martini usados")
        }

        itemsIndexed(receta.productosMartini) { index, producto ->
            Text(
                text = "• $producto",
                modifier = Modifier.padding(horizontal = 24.dp),
                style = MaterialTheme.typography.bodyLarge
            )
            if (index < receta.productosMartini.lastIndex) {
                HorizontalDivider(modifier = Modifier.padding(horizontal = 24.dp))
            }
        }

        item {
            DetailSectionTitle(text = "Ingredientes")
        }

        item {
            PortionScaleSelector(
                selectedMultiplier = multiplicador,
                onMultiplierSelected = { multiplicador = it },
                modifier = Modifier.padding(horizontal = 24.dp)
            )
        }

        itemsIndexed(ingredientesEscalados) { index, ingrediente ->
            IngredientRow(
                ingrediente = ingrediente,
                modifier = Modifier.padding(horizontal = 24.dp)
            )
            if (index < ingredientesEscalados.lastIndex) {
                HorizontalDivider(modifier = Modifier.padding(horizontal = 24.dp))
            }
        }

        item {
            DetailSectionTitle(text = "Preparación")
        }

        itemsIndexed(receta.pasos) { index, paso ->
            Text(
                text = "${index + 1}. $paso",
                modifier = Modifier.padding(horizontal = 24.dp),
                style = MaterialTheme.typography.bodyLarge
            )
        }

    }
}

@Composable
private fun PortionScaleSelector(
    selectedMultiplier: Float,
    onMultiplierSelected: (Float) -> Unit,
    modifier: Modifier = Modifier
) {
    val opciones = listOf(
        0.5f to "×½",
        1f to "×1",
        2f to "×2"
    )

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        opciones.forEach { (multiplicador, etiqueta) ->
            FilterChip(
                selected = selectedMultiplier == multiplicador,
                onClick = { onMultiplierSelected(multiplicador) },
                label = { Text(text = etiqueta) },
                modifier = Modifier
                    .weight(1f)
                    .heightIn(min = 48.dp)
            )
        }
    }
}

@Composable
private fun DetailSectionTitle(text: String) {
    Text(
        text = text,
        modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp),
        style = MaterialTheme.typography.titleLarge
    )
}

@Composable
private fun IngredientRow(
    ingrediente: Ingrediente,
    modifier: Modifier = Modifier
) {
    Text(
        text = "${ingrediente.nombre}: ${ingrediente.cantidad.formateada()} ${ingrediente.unidad}",
        modifier = modifier,
        style = MaterialTheme.typography.bodyLarge
    )
}

private fun Double.formateada(): String {
    return if (this % 1.0 == 0.0) {
        toInt().toString()
    } else {
        toString().replace('.', ',')
    }
}
