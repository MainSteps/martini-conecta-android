package com.mainsteps.martiniconecta.ui.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mainsteps.martiniconecta.model.Division
import com.mainsteps.martiniconecta.ui.components.ContentCard
import com.mainsteps.martiniconecta.ui.components.EmptyState
import com.mainsteps.martiniconecta.ui.components.ScreenHeader
import com.mainsteps.martiniconecta.viewmodel.ExploreViewModel

private const val DuracionTransicionExplorarMs = 300

@Composable
fun ExploreScreen(
    onOpenDetail: (String) -> Unit,
    modifier: Modifier = Modifier,
    exploreViewModel: ExploreViewModel = viewModel()
) {
    val uiState by exploreViewModel.uiState.collectAsStateWithLifecycle()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(vertical = 24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        ScreenHeader(
            title = "Explorar recetas",
            subtitle = "Encuentra preparaciones para cada división de Martini.",
            modifier = Modifier.padding(horizontal = 24.dp)
        )

        OutlinedTextField(
            value = uiState.busqueda,
            onValueChange = exploreViewModel::actualizarBusqueda,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            label = { Text(text = "Buscar por título") },
            placeholder = { Text(text = "Ejemplo: chocolate") },
            singleLine = true,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null
                )
            },
            trailingIcon = if (uiState.busqueda.isNotEmpty()) {
                {
                    IconButton(
                        onClick = { exploreViewModel.actualizarBusqueda("") }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = "Limpiar búsqueda"
                        )
                    }
                }
            } else {
                null
            }
        )

        DivisionFilters(
            selectedDivision = uiState.divisionSeleccionada,
            onDivisionSelected = exploreViewModel::seleccionarDivision
        )

        AnimatedContent(
            targetState = uiState.recetas.isEmpty(),
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            transitionSpec = {
                fadeIn(tween(DuracionTransicionExplorarMs)) togetherWith
                    fadeOut(tween(DuracionTransicionExplorarMs))
            },
            label = "ExploreResultados",
        ) { sinResultados ->
            if (sinResultados) {
                EmptyState(
                    title = "No encontramos recetas",
                    message = "Prueba con otro título o selecciona una división diferente.",
                    modifier = Modifier.padding(horizontal = 24.dp),
                    actionLabel = "Limpiar filtros",
                    onAction = exploreViewModel::limpiarFiltros
                )
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    items(
                        items = uiState.recetas,
                        key = { receta -> receta.id }
                    ) { receta ->
                        ContentCard(
                            title = receta.titulo,
                            description = receta.descripcion,
                            supportingText = receta.division.etiqueta,
                            onClick = { onOpenDetail(receta.id) },
                            modifier = Modifier
                                .padding(horizontal = 24.dp)
                                .animateItem()
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun DivisionFilters(
    selectedDivision: Division?,
    onDivisionSelected: (Division?) -> Unit
) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 24.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            FilterChip(
                selected = selectedDivision == null,
                onClick = { onDivisionSelected(null) },
                label = { Text(text = "Todas") },
                modifier = Modifier.heightIn(min = 48.dp)
            )
        }

        items(Division.entries) { division ->
            FilterChip(
                selected = selectedDivision == division,
                onClick = { onDivisionSelected(division) },
                label = { Text(text = division.etiqueta) },
                modifier = Modifier.heightIn(min = 48.dp)
            )
        }
    }
}
